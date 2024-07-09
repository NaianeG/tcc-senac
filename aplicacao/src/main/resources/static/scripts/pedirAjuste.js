document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const fileInput = document.getElementById('arquivoNovo');
    
    form.addEventListener('submit', function(event) {
        const file = fileInput.files[0];
        if (file && file.size > 2 * 1024 * 1024) { // 2MB em bytes
            alert('O arquivo deve ter no máximo 2MB.');
            event.preventDefault();
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