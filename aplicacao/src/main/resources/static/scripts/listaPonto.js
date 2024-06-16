function toggleMenu() {
    const menuMobile = document.getElementById('menu-mobile');
    const isMenuActive = menuMobile.getAttribute('aria-hidden') === 'false';
    menuMobile.setAttribute('aria-hidden', !isMenuActive);
    if (isMenuActive) {
        menuMobile.classList.remove('menu-mobile-active');
    } else {
        menuMobile.classList.add('menu-mobile-active');
    }
}

function confirmDelete(id) {
    const modal = document.getElementById('confirmModal');
    modal.style.display = 'block';

    const confirmBtn = document.getElementById('confirmBtn');
    confirmBtn.onclick = function() {
        window.location.href = `/deletarPonto/${id}`;
    };
}

function closeModal() {
    const modal = document.getElementById('confirmModal');
    modal.style.display = 'none';
}

window.onclick = function(event) {
    const modal = document.getElementById('confirmModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}
