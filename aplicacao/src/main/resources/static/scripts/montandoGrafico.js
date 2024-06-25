async function fetchData() {
    try {
        const response = await fetch('/dadosGrafico');
        const data = await response.json();
        console.log('Dados dos docentes:', data);
        return data;
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
        return [];
    }
}

function populateSelects(docentes) {
    const selects = document.querySelectorAll('.docente-select');
    selects.forEach(select => {
        docentes.forEach(docente => {
            const option = document.createElement('option');
            option.value = docente[0];
            option.text = docente[0];
            select.appendChild(option);
        });
    });
}

function groupDataByMonth(marcacoes) {
    const groupedData = {};
    marcacoes.forEach(m => {
        const date = new Date(m.data);
        const month = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0');
        if (!groupedData[month]) {
            groupedData[month] = 0;
        }
        groupedData[month] += 1;
    });
    // Ordenar os dados por mês
    return Object.keys(groupedData)
        .sort((a, b) => new Date(a) - new Date(b))
        .map(month => ({ x: new Date(month), y: groupedData[month] }));
}

function groupAjustesByStatus(ajustes) {
    const statusCounts = {
        pendentes: 0,
        aprovados: 0,
        rejeitados: 0
    };
    ajustes.forEach(a => {
        if (a.status === null) {
            statusCounts.pendentes += 1;
        } else if (a.status === true) {
            statusCounts.aprovados += 1;
        } else if (a.status === false) {
            statusCounts.rejeitados += 1;
        }
    });
    return [statusCounts.pendentes, statusCounts.aprovados, statusCounts.rejeitados];
}

function convertMillisecondsToHours(ms) {
    const totalHours = ms / 1000 / 60 / 60;
    const hours = Math.floor(totalHours);
    const minutes = Math.floor((totalHours % 1) * 60);
    return `${hours}h ${minutes}m`;
}

function groupBankHoursData(bancoHoras) {
    return bancoHoras.map(bh => ({
        x: new Date(), // Aqui você pode ajustar para o período correto se necessário
        y: bh.saldoAtual / 1000 / 60 / 60 // Convertendo milissegundos para horas
    }));
}

function updateChart(chart, data) {
    chart.data.datasets[0].data = data;
    chart.update();
}

async function initialize() {
    const docentes = await fetchData();

    if (Array.isArray(docentes) && docentes.length > 0) {
        populateSelects(docentes);

        const lineCtx = document.getElementById('lineChart').getContext('2d');
        const lineChart = new Chart(lineCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Registros de Pontos por Mês',
                    data: [],
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        type: 'time',
                        time: { unit: 'month' },
                        title: { display: true, text: 'Mês' }
                    },
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Qtd. Registros de Ponto' }
                    }
                }
            }
        });

        const barCtx = document.getElementById('barChart').getContext('2d');
        const barChart = new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: ['Pendentes', 'Aprovados', 'Rejeitados'],
                datasets: [{
                    label: 'Ajustes de Ponto',
                    data: [],
                    backgroundColor: [
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Qtd. Ajustes' }
                    }
                }
            }
        });

        const bankHoursCtx = document.getElementById('bankHoursChart').getContext('2d');
        const bankHoursChart = new Chart(bankHoursCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Saldo de Banco de Horas',
                    data: [],
                    borderColor: 'rgb(54, 162, 235)',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        type: 'time',
                        time: { unit: 'month' },
                        title: { display: true, text: 'Mês' }
                    },
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Saldo (Horas)' },
                        ticks: {
                            callback: function(value) {
                                return convertMillisecondsToHours(value * 3600000); // Convertendo horas
                            }
                        }
                    }
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const value = context.raw.y;
                                return convertMillisecondsToHours(value * 3600000); // Ajuste para horas
                            }
                        }
                    }
                }
            }
        });

        document.getElementById('lineChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = selectedDocente === 'all' 
                ? groupDataByMonth(docentes.flatMap(d => d[1])) 
                : groupDataByMonth(docentes.find(d => d[0] === selectedDocente)[1]);
            updateChart(lineChart, data);
        });

        document.getElementById('barChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = selectedDocente === 'all' 
                ? groupAjustesByStatus(docentes.flatMap(d => d[2])) 
                : groupAjustesByStatus(docentes.find(d => d[0] === selectedDocente)[2]);
            updateChart(barChart, data);
        });

        document.getElementById('bankHoursChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = groupBankHoursData(docentes.find(d => d[0] === selectedDocente)[3]);
            updateChart(bankHoursChart, data);
        });

        // Inicializa os gráficos com todos os usuários
        updateChart(lineChart, groupDataByMonth(docentes.flatMap(d => d[1])));
        updateChart(barChart, groupAjustesByStatus(docentes.flatMap(d => d[2])));
    } else {
        console.error('Dados dos docentes não foram carregados corretamente:', docentes);
    }
}

window.onload = initialize;
