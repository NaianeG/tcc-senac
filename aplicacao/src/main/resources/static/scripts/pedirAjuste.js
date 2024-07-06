// Verifica e limita o tamanho do arquivo no front
document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("ajusteForm");
    const fileInput = document.getElementById("arquivoNovo");

    form.addEventListener("submit", function (event) {
        const file = fileInput.files[0];
        if (file && file.size > 2 * 1024 * 1024) { // 2MB em bytes
            event.preventDefault();
            alert("O arquivo não pode ser maior que 2MB.");
        }
    });
});

function logout(){
    window.location.replace("/logout");
}
function apresentaListaPonto(){
    const userId = document.getElementById('idUsuario-data').dataset.attribute;
    window.location.href = `/ponto/listaPonto/${userId}`;
}