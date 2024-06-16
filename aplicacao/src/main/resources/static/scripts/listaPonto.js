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

function closeModal() {
    let modal = document.getElementById("confirmModal");
    modal.style.display = "none";
}

function confirmDelete(id, idUsuario, page, size) {
    let modal = document.getElementById('confirmModal');
    modal.style.display = 'block';

    let confirmBtn = document.getElementById('confirmBtn');
    confirmBtn.onclick = function() {
        window.location.href = `/ponto/deletarPonto/${id}?idUsuario=${idUsuario}&page=${page}&size=${size}`;
    };
}

function closeSuccessModal() {
    let modal = document.getElementById("successModal");
    modal.style.display = "none";
}

window.onload = function() {
    let modal = document.getElementById("successModal");
    if (modal) {
        modal.style.display = "block";
    }
};

window.onclick = function(event) {
    let modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }

    let successModal = document.getElementById('successModal');
    if (event.target == successModal) {
        successModal.style.display = 'none';
    }
}