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
    
    let dataAtual = new Date();

    let ano = dataAtual.getFullYear();
    let mes = String(dataAtual.getMonth() + 1).padStart(2, '0');
    let dia = String(dataAtual.getDate()).padStart(2, '0');
    let dataFormatada = ano + '-' + mes + '-' + dia;
    document.getElementById('data').value = dataFormatada;

    let hora = String(dataAtual.getHours()).padStart(2, '0');
    let minuto = String(dataAtual.getMinutes()).padStart(2, '0');
    let segundo = String(dataAtual.getSeconds()).padStart(2, '0');
    let horaAtual = hora + ':' + minuto + ':' + segundo;
    document.getElementById('horaEntrada').value = horaAtual;
}

function logout(){
    window.location.replace("http://localhost:8080/logout");
}