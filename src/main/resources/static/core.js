function toggleButton(ref, btnID) {
    document.getElementById(btnID).disabled = false;
}

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
	body: requestInfo.body,
	credentials: 'include'})
	.then(response => {
	    if (!response.ok) {
		throw new Error('Network response was not ok');
	    }
	    return response.json();})
	.then(data => {
	    if (data.status) {
		handle(data.data);
	    } else {
		showErrorMsg(data.extraMsg)
	    }
	})
	.catch(error => {
	    console.error('There was a problem with your fetch operation:', error);
	});
}

function showSuccessMsg(msg) {
    document.getElementById("lblsuccess").innerHTML = "msg";
    document.getElementById("lblsuccess").style.display = "block";
    setTimeout(function() {
	document.getElementById("lblsuccess").style.display = "none";
    }, 3000);
}

function showErrorMsg(msg) {
    document.getElementById("lblerror").innerHTML = msg;
    document.getElementById("lblerror").style.display = "block";
    setTimeout(function() {
	document.getElementById("lblerror").style.display = "none";
    }, 3000);
}


