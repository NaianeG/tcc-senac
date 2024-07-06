document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("confirmModal");
    var confirmBtn = document.getElementById("confirmBtn");
    var cancelBtn = document.querySelector(".cancelBtn");
    var closeBtn = document.querySelector(".close");

    function openModal(pontoId, usuarioId, currentPage, size) {
        confirmBtn.onclick = function() {
            window.location.href = "/ponto/deletarPonto/" + pontoId + "?idUsuario=" + usuarioId + "&page=" + currentPage + "&size=" + size;
        };
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    window.confirmDelete = openModal;
    closeBtn.onclick = closeModal;
    cancelBtn.onclick = closeModal;

    window.addEventListener("click", function(event) {
        if (event.target == modal) {
            closeModal();
        }
    });
});

// script modal erro
document.addEventListener("DOMContentLoaded", function () {
    var errorModal = document.getElementById("errorModal");
    var errorMessageElement = document.getElementById("errorMessage");
    var closeErrorBtn = document.querySelector("#errorModal .close");
    var okBtn = document.querySelector("#errorModal .okBtn");

    function openErrorModal(message) {
        errorMessageElement.textContent = message;
        errorModal.style.display = "block";
    }

    function closeErrorModal() {
        errorModal.style.display = "none";
    }

    window.openErrorModal = openErrorModal;
    closeErrorBtn.onclick = closeErrorModal;
    okBtn.onclick = closeErrorModal;

    window.addEventListener("click", function(event) {
        if (event.target == errorModal) {
            closeErrorModal();
        }
    });

    // Mostrar o modal de erro se houver uma mensagem de erro
    let errorMessage = document.getElementById('errorMessageContent').textContent;
    if (errorMessage) {
        openErrorModal(errorMessage);
    }
});


// Remover duplicação de eventos de clique no window
window.removeEventListener("click", window.clickHandler);
window.clickHandler = function(event) {
    var modal = document.getElementById("confirmModal");
    var successModal = document.getElementById("successModal");
    var errorModal = document.getElementById("errorModal");

    if (event.target == modal) {
        modal.style.display = "none";
    }
    if (event.target == successModal) {
        successModal.style.display = "none";
    }
    if (event.target == errorModal) {
        errorModal.style.display = "none";
    }
};

window.addEventListener("click", window.clickHandler);

function logout() {
    window.location.replace("/logout");
}

function apresentaListaPonto() {
    let idUsuario = document.getElementById("idUsuario-data").getAttribute("data-attribute");
    window.location.replace("/ponto/listaPonto/" + idUsuario);
}
