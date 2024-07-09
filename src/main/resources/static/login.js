window.onload = () => {

    const login = document.querySelector("#login");
    if (login) {
	login.addEventListener("submit", loginHandler);
    }
};

/**
 * A handler to login by password.
 */
function loginHandler(e) {
    e.preventDefault();
    let inputValue = document.getElementById("item").value;
    sendRequest({method: "GET", path: "/token", params: {password: md5(inputValue)}}, data => {
	window.location.href = "/";
    });
}

