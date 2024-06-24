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
        const defaultOption = document.createElement('option');
        defaultOption.value = 'all';
        defaultOption.text = 'Todos os Docentes';
        select.appendChild(defaultOption);

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
    return Object.keys(groupedData).map(month => ({ x: new Date(month), y: groupedData[month] }));
}

function getAdjustmentsData(ajustes) {
    const statusCount = { pendente: 0, aprovado: 0, reprovado: 0 };
    ajustes.forEach(a => {
        if (a.status === null) {
            statusCount.pendente += 1;
        } else if (a.status) {
            statusCount.aprovado += 1;
        } else {
            statusCount.reprovado += 1;
        }
    });
    return [
        statusCount.pendente,
        statusCount.aprovado,
        statusCount.reprovado
    ];
}

function getDataForDocente(docentes, docenteName) {
    if (docenteName === 'all') {
        const allMarcacoes = [];
        const allAjustes = [];
        docentes.forEach(docente => {
            allMarcacoes.push(...docente[1]);
            allAjustes.push(...docente[2]);
        });
        return {
            registros: groupDataByMonth(allMarcacoes),
            ajustes: getAdjustmentsData(allAjustes)
        };
    } else {
        const docente = docentes.find(d => d[0] === docenteName);
        const marcacoes = docente ? docente[1] : [];
        const ajustes = docente ? docente[2] : [];
        return {
            registros: groupDataByMonth(marcacoes),
            ajustes: getAdjustmentsData(ajustes)
        };
    }
}

function updateChart(chart, data) {
    chart.data.datasets[0].data = data;
    chart.update();
}

function updateBarChart(chart, data) {
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

        const adjustmentCtx = document.getElementById('adjustmentChart').getContext('2d');
        const adjustmentChart = new Chart(adjustmentCtx, {
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
                        title: { display: true, text: 'Quantidade de Ajustes' }
                    }
                }
            }
        });

        document.getElementById('lineChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getDataForDocente(docentes, selectedDocente);
            updateChart(lineChart, data.registros);
        });

        document.getElementById('adjustmentChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getDataForDocente(docentes, selectedDocente);
            updateBarChart(adjustmentChart, data.ajustes);
        });

        const initialData = getDataForDocente(docentes, 'all');
        updateChart(lineChart, initialData.registros);
        updateBarChart(adjustmentChart, initialData.ajustes);
    } else {
        console.error('Dados dos docentes não foram carregados corretamente:', docentes);
    }
}

window.onload = initialize;
