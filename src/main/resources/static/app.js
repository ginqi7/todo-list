// app.js

var editItem;
var inputItem;

window.onload = () => {
    inputItem = document.querySelector(".input")
    swipeListener()
    refresh()
};


function groupBy (array, key) {
    return array.reduce((result, currentValue) => {
        const groupKey = currentValue[key];
        if (!result[groupKey]) {
            result[groupKey] = [];
        }
        result[groupKey].push(currentValue);
        return result;
    }, {});
};


function refresh() {
    // Show all Todo list items.
    sendRequest({method: "GET", path: "/list"}, data => {
	classifyTask(data)
    });
}

function classifyTask(data) {
    groupedTasks = groupBy(data, "status")
    miniVue.tasks = groupedTasks["TODO"] || []
    miniVue.doneList = groupedTasks["DONE"] || []
}

/**
 * A handler to handle submit event.
 * There are two different event:
 * 1. add a new todo item.
 * 2. edit a old item.
 */
function submitHandler(e) {
    e.preventDefault();
    let inputValue = miniVue.inputTodo
    if (!inputValue || inputValue.trim().length === 0) {
	return
    }
    let submitType = miniVue.submitType
    miniVue.inputTodo = ""
    if (submitType == "Edit") {
	sendRequest({method: "POST", path: "/update", params:{todoId: editItem.id}, body: inputValue}, 
		    data => {
			refresh()
		    });
	miniVue.submitType = "Submit";

    } else {
	var tasks = miniVue.tasks;
	tasks.unshift({status : "TODO", data : inputValue}); 
	miniVue.tasks = copyData(tasks)
	sendRequest({method: "POST",
		     path: "/add",
		     body: inputValue
		    }, data => {
			refresh()
		    });
    }
}

function copyData(data) {
    return JSON.parse(JSON.stringify(data));
}


function editHandler (e) {
    var element = e.target.parentNode.parentNode;
    miniVue.inputTodo = element.querySelector(".data").innerText
    miniVue.submitType = "Edit";
    editItem = element
    inputItem.focus()
}

function deleteHandler(e) {
    var element = e.target.parentNode.parentNode;
    confirm("Are you sure you want to delete this todo item?", () => {
	sendRequest({method: "DELETE", path: "/delete", params: {todoId: element.id}}, data => {
	    refresh();
	});
    })
}

function confirm(prompt, callback) {
    var box = document.querySelector(".confirm-box");
    box.classList.remove("hidden")
    miniVue.prompt = prompt;
    var element = document.querySelector(".confirm");
    element.onclick = function() {
	callback()
	box.classList.add("hidden")
    }
    var cancel = document.querySelector(".cancel");
    cancel.onclick = function() {
	box.classList.add("hidden")
    }
}

function toggleTaskStatus(tasks, id) {
    const task = tasks.find(task => task.id == id);
    tasks = tasks.filter(task => task.id != id);
    if (task == "TODO") {
	task.status = "DONE"
    } else {
	task.status = "TODO"
    }
    return [task, tasks]
}

function toggleStatus(e) {
    var element = e.target.parentNode;
    if (e.target.checked) {
	[task, miniVue.tasks] = toggleTaskStatus(miniVue.tasks, element.id)
	miniVue.doneList.unshift(task)
	miniVue.doneList = copyData(miniVue.doneList)
    } else {
	[task, miniVue.doneList] = toggleTaskStatus(miniVue.doneList, element.id)
	miniVue.tasks.unshift(task)
	miniVue.tasks = copyData(miniVue.tasks)
    }
    sendRequest({method: "GET", path: "/toggleStatus", params: {todoId: element.id}}, data => {
	refresh();
    });
}

function showTodo(e) {
    miniVue.showTodo= ""
    miniVue.todoTab= "underline"
    miniVue.showDone= "hidden"
    miniVue.doneTab= ""
}

function showDone(e) {
    miniVue.showTodo= "hidden"
    miniVue.todoTab= ""
    miniVue.showDone= ""
    miniVue.doneTab= "underline"
}


function swipeListener() {
    
    const app = document.querySelector(".app")

    const observer = new MutationObserver(() => {
	const swipeContainers = document.querySelectorAll('li');
	swipeContainers.forEach(container => {
            let startX, currentX;

            container.addEventListener('touchstart', (event) => {
		startX = event.touches[0].clientX;
            });

            container.addEventListener('touchmove', (event) => {
		currentX = event.touches[0].clientX;
		const deltaX = currentX - startX;
            });

            container.addEventListener('touchend', (event) => {
		const deltaX = currentX - startX;
		var li = event.target.parentNode
		var options = li.querySelector(".options")
		if (deltaX < -50) { 
		    options.classList.remove('hidden')
		}
		if (deltaX > 50) { 
		    options.classList.add('hidden')
		}
            });
	})

    });
    
    observer.observe(app, { childList: true, subtree: true });

    ;
}
