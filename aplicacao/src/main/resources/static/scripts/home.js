function toggleMenu() {
    
    const menuMobile = document.getElementById("menu-mobile")

    if(menuMobile.className === "menu-mobile-active") {
        menuMobile.className = "menu-mobile"
    } else {
        menuMobile.className = "menu-mobile-active"
    }
}

function apresentarMenuDocentes() {
    
    const menuDocentes = document.getElementById("container-direita")

    if (menuDocentes.className === "container-direita"){
        menuDocentes.className = "container-direita-active"
    } else {
        menuDocentes.className = "container-direita"
    }
}


function preencherDataAtual() {
    
    var dataAtual = new Date();

    var ano = dataAtual.getFullYear();
    var mes = String(dataAtual.getMonth() + 1).padStart(2, '0');
    var dia = String(dataAtual.getDate()).padStart(2, '0');
    var dataFormatada = ano + '-' + mes + '-' + dia;
    document.getElementById('data').value = dataFormatada;

    var hora = String(dataAtual.getHours()).padStart(2, '0');
    var minuto = String(dataAtual.getMinutes()).padStart(2, '0');
    var segundo = String(dataAtual.getSeconds()).padStart(2, '0');
    var horaAtual = hora + ':' + minuto + ':' + segundo;
    document.getElementById('horaEntrada').value = horaAtual;
}

function logout(){
    window.location.replace("http://localhost:8080/logout");
}