function logout(){
    window.location.replace("/logout");
}

function apresentaListaPonto(){
    const userId = document.getElementById('idUsuario-data').dataset.attribute;
    window.location.href = `/ponto/listaPonto/${userId}`;
}