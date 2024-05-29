function toggleMenu() {    
    const menuMobile = document.getElementById("menu-mobile")

    if(menuMobile.className === "menu-mobile-active") {
        menuMobile.className = "menu-mobile"
    } else {
        menuMobile.className = "menu-mobile-active"
    }
}

function apresentarRegistroDePonto(){
    const menuPonto = document.getElementById("container-registro-ponto")

    if (menuPonto.className === "container-registro-ponto"){
        menuPonto.className = "container-registro-ponto-ativo"
    } else {
        menuPonto.className = "container-registro-ponto"
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