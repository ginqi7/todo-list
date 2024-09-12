/**
 * A handler to login by password.
 */
function loginHandler(e) {
    e.preventDefault();
    let inputValue = document.querySelector(".password").value
    sendRequest({method: "GET", path: "/token", params: {password: md5(inputValue)}}, data => {
	window.location.href = "/";
    });
}

