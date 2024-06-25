// script.js

window.onload = function() {
    console.log("Window loaded");

    var modal = document.getElementById("modal");
    console.log("Modal element:", modal);

    var span = document.getElementsByClassName("close-button")[0];
    console.log("Close button:", span);

    var errorMessage = document.getElementById("msgErroUsernamePassword");
    var logoutMessage = document.getElementById("msgDeslogar");
    console.log("Error message element:", errorMessage);
    console.log("Logout message element:", logoutMessage);

    if ((errorMessage && errorMessage.innerHTML.trim() !== "") || (logoutMessage && logoutMessage.innerHTML.trim() !== "")) {
        console.log("Displaying modal");
        modal.style.display = "block";
    } else {
        console.log("No message to display");
    }

    span.onclick = function() {
        console.log("Close button clicked");
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            console.log("Clicked outside modal");
            modal.style.display = "none";
        }
    }
}
