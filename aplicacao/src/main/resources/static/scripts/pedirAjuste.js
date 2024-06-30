function logout(){
    window.location.replace("http://localhost:8080/logout");
}
function apresentaListaPonto(){
    const userId = document.getElementById('idUsuario-data').dataset.attribute;
    window.location.href = `/ponto/listaPonto/${userId}`;
}