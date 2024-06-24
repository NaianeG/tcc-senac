function confirmDelete(pontoId, usuarioId, currentPage, size) {
    var modal = document.getElementById("confirmModal");
    var confirmBtn = document.getElementById("confirmBtn");

    modal.style.display = "block";

    confirmBtn.onclick = function() {
        window.location.href = "/ponto/deletarPonto/" + pontoId + "?idUsuario=" + usuarioId + "&page=" + currentPage + "&size=" + size;
    };
}

function closeModal() {
    var modal = document.getElementById("confirmModal");
    modal.style.display = "none";
}

function closeSuccessModal() {
    var successModal = document.getElementById("successModal");
    successModal.style.display = "none";
}

// Fechar modal quando o usuário clica fora dele
window.onclick = function(event) {
    var modal = document.getElementById("confirmModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
    var successModal = document.getElementById("successModal");
    if (event.target == successModal) {
        successModal.style.display = "none";
    }
}

function logout(){
    window.location.replace("http://localhost:8080/logout");
}
