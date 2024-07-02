document.addEventListener("DOMContentLoaded", function() {
    // Verifique se há um parâmetro de erro na URL
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        document.getElementById('modal').style.display = 'block';
    }
});

function closeModal() {
    var modal = document.getElementById("modal");
    modal.style.display = "none";
}

// Fechar modal quando o usuário clica fora dele
window.onclick = function(event) {
    var modal = document.getElementById("modal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
