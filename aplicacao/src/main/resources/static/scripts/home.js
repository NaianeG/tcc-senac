function apresentarRegistroDePonto(){
    const menuPonto = document.getElementById("container-registro-ponto")

    if (menuPonto.className === "container-registro-ponto"){
        menuPonto.className = "container-registro-ponto-ativo"
    } 
}

function preencherDataAtual() {
    let dataAtual = new Date();

    let ano = dataAtual.getFullYear();
    let mes = String(dataAtual.getMonth() + 1).padStart(2, '0');
    let dia = String(dataAtual.getDate()).padStart(2, '0');
    let dataFormatada = ano + '-' + mes + '-' + dia;
    document.getElementById('data').value = dataFormatada;

    atualizarHora();
    setInterval(atualizarHora, 1000);
}

function atualizarHora() {
    let dataAtual = new Date();
    let hora = String(dataAtual.getHours()).padStart(2, '0');
    let minuto = String(dataAtual.getMinutes()).padStart(2, '0');
    let segundo = String(dataAtual.getSeconds()).padStart(2, '0');
    let horaAtual = hora + ':' + minuto + ':' + segundo;
    document.getElementById('horaEntrada').value = horaAtual;
}

function logout(){
    window.location.replace("http://localhost:8080/logout");
}

function apresentaListaPonto(){
    let idUsuario = document.getElementById("idUsuario-data").getAttribute("data-attribute");
    window.location.replace("/ponto/listaPonto/"+idUsuario)
}

function apresentarAjustesDePonto(){
    window.location.href = "/listarAjustes";
}