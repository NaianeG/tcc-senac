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
      var errorMessage = "Geolocalização não é suportada por este navegador.";
      console.log(errorMessage);
      document.getElementById('localizacaoInput').value = errorMessage;
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
  document.getElementById('localizacaoInput').value = mapsLink;
  console.log("Google Maps Link: " + mapsLink);

  var linkElement = document.getElementById("mapsLink");
  if (linkElement) {
      linkElement.href = mapsLink;
      linkElement.style.display = "block";
  }
}

function showError(error) {
  var errorMessage;
  switch (error.code) {
      case error.PERMISSION_DENIED:
          errorMessage = "Usuário negou a solicitação de Geolocalização.";
          break;
      case error.POSITION_UNAVAILABLE:
          errorMessage = "As informações de localização não estão disponíveis.";
          break;
      case error.TIMEOUT:
          errorMessage = "A solicitação para obter a localização expirou.";
          break;
      case error.UNKNOWN_ERROR:
          errorMessage = "Um erro desconhecido ocorreu.";
          break;
  }
  console.log(errorMessage);
  document.getElementById('localizacaoInput').value = errorMessage;
}

getLocation();

function apresentarRegistroDePonto() {
  const menuPonto = document.getElementById("container-registro-ponto");

  if (menuPonto.className === "container-registro-ponto") {
      menuPonto.className = "container-registro-ponto-ativo";
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
  document.getElementById('horaSaida').value = horaAtual;

}

function logout() {
  window.location.replace("/logout");
}

function apresentaListaPonto() {
  let idUsuario = document.getElementById("idUsuario-data").getAttribute("data-attribute");
  window.location.replace("/ponto/listaPonto/" + idUsuario);
}

function apresentarAjustesDePonto() {
  window.location.href = "/listarAjustes";
}

// Função para renderizar o gráfico de pizza na home do docente
async function fetchDadosBancoHoras() {
  try {
      const response = await fetch('/dadosGraficoHome');
      const data = await response.json();
      return data;
  } catch (error) {
      console.error('Erro ao carregar dados do banco de horas:', error);
      return null;
  }
}

function converterHorasParaMilissegundos(horasStr) {
  const [horas, minutos, segundos] = horasStr.split(':').map(Number);
  return ((horas * 3600) + (minutos * 60) + segundos) * 1000;
}

async function inicializarGraficoHome() {
  const dados = await fetchDadosBancoHoras();

  if (dados) {
      const saldoAtual = converterHorasParaMilissegundos(dados.saldoAtual);
      const saldoMensal = dados.saldoMensal * 1000 * 60 * 60; // Convertendo horas de volta para milissegundos

      const ctx = document.getElementById('graficoHome').getContext('2d');
      const graficoHome = new Chart(ctx, {
          type: 'pie',
          data: {
              labels: ['Saldo Atual', 'Saldo Restante'],
              datasets: [{
                  data: [saldoAtual, saldoMensal - saldoAtual],
                  backgroundColor: ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'],
                  borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
                  borderWidth: 1
              }]
          },
          options: {
              responsive: true,
              maintainAspectRatio: false,
              plugins: {
                  tooltip: {
                      callbacks: {
                          label: function(tooltipItem) {
                              const valor = tooltipItem.raw;
                              const horas = Math.floor(valor / 3600000);
                              const minutos = Math.floor((valor % 3600000) / 60000);
                              return `${horas}h ${minutos}m`;
                          }
                      }
                  }
              }
          }
      });
  } else {
      console.error('Dados do banco de horas não encontrados.');
  }
}

document.addEventListener('DOMContentLoaded', () => {
  inicializarGraficoHome();
});

