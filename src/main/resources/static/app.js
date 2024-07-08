// app.js
window.onload = () => {
    const form = document.querySelector("#addForm");
    let items = document.getElementById("items");
    let submit = document.getElementById("submit");
    let editItem = null;

    form.addEventListener("submit", submitHandler);
    items.addEventListener("click", clickHandler);
    
    // Show all Todo list items.
    sendRequest({method: "GET", path: "/list"}, data => {
	for (const item of data) {
	    showTodoItem(item);
	}
    });
};

/**
 * Send a request to backend.
 * @param {obj} requestInfo - some request info like: method, path, params, body
 * @param {function} handle - a function to handle request response
 */
function sendRequest(requestInfo, handle) {
    let path = `http://${window.location.host}${requestInfo.path}`;
    if(requestInfo && requestInfo.params) {
	path = path + "?" + 
	    Object.entries(requestInfo.params)
	    .map(([key, value]) => {return key + "=" + value;})
	    .join('&');
    }
    fetch(path, {
	method: requestInfo.method,
	body: requestInfo.body})
	.then(response => {
	    if (!response.ok) {
		throw new Error('Network response was not ok');
	    }
	    return response.json();})
	.then(data => {
	    console.log(data)
	    handle(data)})
	.catch(error => {
	    console.error('There was a problem with your fetch operation:', error);
	});
}

/**
 * Add and show a todo list item in todo list.
 * @param {obj} item - a request respone contained todo item information.
 */
function showTodoItem(item) {
    let li = document.createElement("li");
    li.className = "list-group-item";
    li.id = item.id;
    let statusButton = document.createElement("button");
    statusCls = item.status == "TODO" ? "btn-danger" : "btn-success";
    statusButton.className = "btn btn-sm status " + statusCls;
    statusButton.appendChild(document.createTextNode(item.status));

    let deleteButton = document.createElement("button");
    deleteButton.className = "btn-danger btn btn-sm float-right delete";
    deleteButton.appendChild(document.createTextNode("Delete"));

    let editButton = document.createElement("button");
    editButton.className = "btn-success btn btn-sm float-right edit";
    editButton.appendChild(document.createTextNode("Edit"));

    li.appendChild(statusButton);
    li.appendChild(document.createTextNode(item.data));
    li.appendChild(deleteButton);
    li.appendChild(editButton);

    items.appendChild(li);
}

/**
 * A handler to handle submit event.
 * There are two different event:
 * 1. add a new todo item.
 * 2. edit a old item.
 */
function submitHandler(e) {
    e.preventDefault();
    let inputValue = document.getElementById("item").value;
    if (submit.value == "EDIT") {
	sendRequest({method: "POST", path: "/update", params:{todoId: editItem.parentNode.id}, body: inputValue}, data => {
	    editItem.parentNode.childNodes[1].data = inputValue;
	    editItem=null;
	    document.getElementById("item").value = "";
	    submit.value = "Submit";
	});

    } else {
	sendRequest({method: "POST",
		     path: "/add",
		     body: inputValue
		    }, data => {
			showTodoItem(data);
			document.getElementById("item").value = "";
		    });
    }
}

/**
 * A handler to handle client event.
 * There are three different events:
 * 1. delete a todo item.
 * 2. edit a old item.
 * 3. toggle todo item's status.
 */
function clickHandler(e) {
    e.preventDefault();
    let li = e.target.parentNode;
    if (e.target.classList.contains("delete")) {
	if (confirm("Are you Sure?")) {
	    sendRequest({method: "DELETE", path: "/delete", params: {todoId: li.id}}, data => {
		items.removeChild(li);
		document.getElementById("lblsuccess").innerHTML = "Text deleted successfully";

		document.getElementById("lblsuccess").style.display = "block";

		setTimeout(function() {
		    document.getElementById("lblsuccess").style.display = "none";
		}, 3000);
	    });

	}
    } else if (e.target.classList.contains("edit")) {
	document.getElementById("item").value = li.childNodes[1].data;
	submit.value = "EDIT";
	editItem = e.target
    } else if (e.target.classList.contains("status")) {
	sendRequest({method: "GET", path: "/toggleStatus", params: {todoId: li.id}}, data => {
	    li.childNodes[0].innerText = data.status;
	    if (!li.childNodes[0].classList.replace("btn-success", "btn-danger")) {
		li.childNodes[0].classList.replace("btn-danger", "btn-success")
	    }
	    
	});
    }
}

function toggleButton(ref, btnID) {
    document.getElementById(btnID).disabled = false;
}
