// Geolocalização
function getLocation() {
  console.log("Obtendo localização...");
  if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(showPosition, showError, {
          enableHighAccuracy: true, // Alta precisão
          timeout: 10000, // Tempo limite em milissegundos
          maximumAge: 0 // Não usar localização armazenada em cache
      });
  } else {
      console.log("Geolocalização não é suportada por este navegador.");
  }
}

function showPosition(position) {
  var latitude = position.coords.latitude;
  var longitude = position.coords.longitude;
  var accuracy = position.coords.accuracy; // Precisão da localização em metros
  console.log("Latitude: " + latitude);
  console.log("Longitude: " + longitude);
  console.log("Precisão: " + accuracy + " metros");

  var mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;
  console.log("Google Maps Link: " + mapsLink);

  var linkElement = document.getElementById("mapsLink");
  if (linkElement) {
      linkElement.href = mapsLink;
      linkElement.style.display = "block";
  }
}

function showError(error) {
  switch (error.code) {
      case error.PERMISSION_DENIED:
          console.log("Usuário negou a solicitação de Geolocalização.");
          break;
      case error.POSITION_UNAVAILABLE:
          console.log("As informações de localização não estão disponíveis.");
          break;
      case error.TIMEOUT:
          console.log("A solicitação para obter a localização expirou.");
          break;
      case error.UNKNOWN_ERROR:
          console.log("Um erro desconhecido ocorreu.");
          break;
  }
}

getLocation();

// Função para lidar com erros na obtenção da localização
function showError(error) {
  switch (error.code) {
      case error.PERMISSION_DENIED:
          console.log("Usuário negou a solicitação de Geolocalização.");
          break;
      case error.POSITION_UNAVAILABLE:
          console.log("As informações de localização não estão disponíveis.");
          break;
      case error.TIMEOUT:
          console.log("A solicitação para obter a localização expirou.");
          break;
      case error.UNKNOWN_ERROR:
          console.log("Um erro desconhecido ocorreu.");
          break;
  }
}

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
