function toggleMenu() {
    let menu = document.getElementById("menu-mobile");
    let isHidden = menu.getAttribute("aria-hidden") === "true";
    menu.setAttribute("aria-hidden", !isHidden);
    if (isHidden) {
        menu.classList.add("menu-mobile-active");
    } else {
        menu.classList.remove("menu-mobile-active");
    }
}

function confirmDelete(id) {
    let modal = document.getElementById('confirmModal');
    modal.style.display = 'block';

    let confirmBtn = document.getElementById('confirmBtn');
    confirmBtn.onclick = function () {
        window.location.href = `/deletarPonto/${id}`;
    };
}

function closeModal() {
    let modal = document.getElementById('confirmModal');
    modal.style.display = 'none';
}

window.onclick = function (event) {
    let modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}

function logout() {
    window.location.replace("http://localhost:8080/logout");
}
