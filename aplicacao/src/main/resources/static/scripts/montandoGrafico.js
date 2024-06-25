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
        // Remover opções anteriores para evitar duplicação
        select.innerHTML = '';

        // Adicionar a opção "Todos os Usuários" uma vez
        const allOption = document.createElement('option');
        allOption.value = 'all';
        allOption.text = 'Todos os Usuários';
        select.appendChild(allOption);

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

    // Ordenar os dados por data
    const sortedData = Object.keys(groupedData)
        .sort((a, b) => new Date(a) - new Date(b))
        .map(month => ({ x: new Date(month), y: groupedData[month] }));
        
    return sortedData;
}

function getDataForDocente(docentes, docenteName) {
    if (docenteName === 'all') {
        const allMarcacoes = docentes.flatMap(d => d[1]);
        return groupDataByMonth(allMarcacoes);
    } else {
        const docente = docentes.find(d => d[0] === docenteName);
        const marcacoes = docente ? docente[1] : [];
        return groupDataByMonth(marcacoes);
    }
}

function groupAdjustmentsByStatus(ajustes) {
    const statusCounts = { pendentes: 0, aprovados: 0, rejeitados: 0 };
    ajustes.forEach(a => {
        if (a.status === null) {
            statusCounts.pendentes++;
        } else if (a.status === true) {
            statusCounts.aprovados++;
        } else {
            statusCounts.rejeitados++;
        }
    });
    return [statusCounts.pendentes, statusCounts.aprovados, statusCounts.rejeitados];
}

function getAdjustmentsData(docentes, docenteName) {
    if (docenteName === 'all') {
        const allAjustes = docentes.flatMap(d => d[2]);
        return groupAdjustmentsByStatus(allAjustes);
    } else {
        const docente = docentes.find(d => d[0] === docenteName);
        const ajustes = docente ? docente[2] : [];
        return groupAdjustmentsByStatus(ajustes);
    }
}

function groupBankHoursData(bancoHoras) {
    return bancoHoras.map(entry => ({
        x: new Date(entry.data),
        y: entry.saldoAtual
    }));
}

function getBankHoursData(docentes, docenteName) {
    if (docenteName === 'all') {
        const allBankHours = docentes.flatMap(d => d[3]);
        return groupBankHoursData(allBankHours);
    } else {
        const docente = docentes.find(d => d[0] === docenteName);
        const bankHours = docente ? docente[3] : [];
        return groupBankHoursData(bankHours);
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

        const barCtx = document.getElementById('barChart').getContext('2d');
        const barChart = new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: ['Pendentes', 'Aprovados', 'Rejeitados'],
                datasets: [{
                    label: 'Ajustes',
                    data: [0, 0, 0],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 206, 86, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 206, 86, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        const bankCtx = document.getElementById('bankHoursChart').getContext('2d');
        const bankHoursChart = new Chart(bankCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Saldo Atual de Banco de Horas',
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
                        title: { display: true, text: 'Saldo Atual' }
                    }
                }
            }
        });

        // Atualizar gráficos com dados de "Todos os Usuários" por padrão
        updateChart(lineChart, getDataForDocente(docentes, 'all'));
        updateBarChart(barChart, getAdjustmentsData(docentes, 'all'));
        updateChart(bankHoursChart, getBankHoursData(docentes, 'all'));

        document.querySelectorAll('.docente-select').forEach(select => {
            select.addEventListener('change', (event) => {
                const selectedDocente = event.target.value;
                if (select.id === 'lineChartSelect') {
                    const data = getDataForDocente(docentes, selectedDocente);
                    updateChart(lineChart, data);
                } else if (select.id === 'barChartSelect') {
                    const data = getAdjustmentsData(docentes, selectedDocente);
                    updateBarChart(barChart, data);
                } else if (select.id === 'bankHoursChartSelect') {
                    const data = getBankHoursData(docentes, selectedDocente);
                    updateChart(bankHoursChart, data);
                }
            });
        });
    } else {
        console.error('Dados dos docentes não foram carregados corretamente:', docentes);
    }
}

window.onload = initialize;
