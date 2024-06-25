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

function populateSelect(selectId, docentes, includeAllOption = false) {
    const select = document.getElementById(selectId);
    select.innerHTML = '';  // limpando opções previas
    if (includeAllOption) {
        const allOption = document.createElement('option');
        allOption.value = 'all';
        allOption.text = 'Todos os Usuários';
        select.appendChild(allOption);
    }
    docentes.forEach(docente => {
        const option = document.createElement('option');
        option.value = docente[0];
        option.text = docente[0];
        select.appendChild(option);
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

    const monthsInRange = getMonthsInRange(marcacoes);
    monthsInRange.forEach(month => {
        if (!groupedData[month]) {
            groupedData[month] = 0;
        }
    });

    return Object.keys(groupedData)
        .sort((a, b) => new Date(a) - new Date(b))
        .map(month => ({ x: new Date(month), y: groupedData[month] }));
}

function getMonthsInRange(marcacoes) {
    const dates = marcacoes.map(m => new Date(m.data));
    const minDate = new Date(Math.min.apply(null, dates));
    const maxDate = new Date(Math.max.apply(null, dates));

    const months = [];
    const current = new Date(minDate.getFullYear(), minDate.getMonth(), 1);

    while (current <= maxDate) {
        const month = current.getFullYear() + '-' + (current.getMonth() + 1).toString().padStart(2, '0');
        months.push(month);
        current.setMonth(current.getMonth() + 1);
    }

    return months;
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

function convertMillisecondsToHoursAndMinutes(ms) {
    const totalMinutes = ms / 1000 / 60;
    const hours = Math.floor(totalMinutes / 60);
    const minutes = Math.floor(totalMinutes % 60);
    return { hours, minutes, formatted: `${hours}h ${minutes}m` };
}

function groupBankHoursData(bancoHoras) {
    return bancoHoras.map(bh => {
        const saldoRestante = bh.saldoMensal - (bh.saldoAtual / 3600000); // Convertendo milissegundos para horas
        return [bh.saldoAtual / 3600000, saldoRestante > 0 ? saldoRestante : 0]; // Convertendo milissegundos para horas
    });
}

function groupHoursWorkedByWeek(marcacoes) {
    const groupedData = {};
    marcacoes.forEach(m => {
        const date = new Date(m.data);
        const weekLabel = getWeekLabel(date);
        if (!groupedData[weekLabel]) {
            groupedData[weekLabel] = 0;
        }
        const entrada = new Date(date.toDateString() + ' ' + m.horaEntrada);
        const saida = m.horaSaida ? new Date(date.toDateString() + ' ' + m.horaSaida) : new Date();

        if (entrada && saida && saida > entrada) {
            groupedData[weekLabel] += (saida - entrada) / 1000 / 60 / 60; // Convertendo milissegundos para horas
        }
    });
    return Object.keys(groupedData)
        .sort((a, b) => new Date(a.split(' ')[1]) - new Date(b.split(' ')[1]))
        .map(weekLabel => ({ x: weekLabel, y: groupedData[weekLabel] }));
}

function getWeekNumber(d) {
    const date = new Date(d.getFullYear(), d.getMonth(), d.getDate());
    const firstDayOfYear = new Date(date.getFullYear(), 0, 1);
    const pastDaysOfYear = (date - firstDayOfYear) / 86400000;
    return Math.ceil((pastDaysOfYear + firstDayOfYear.getDay() + 1) / 7);
}

function getWeekLabel(date) {
    const weekNumber = getWeekNumber(date);
    const month = date.toLocaleString('default', { month: 'long' });
    return `Semana ${weekNumber} de ${month}`;
}

function updateChart(chart, data, xLabelCallback = null) {
    chart.data.datasets[0].data = data;
    if (xLabelCallback) {
        chart.options.scales.x.ticks.callback = xLabelCallback;
    }
    chart.update();
}

function updatePieChart(chart, data) {
    chart.data.datasets[0].data = data[0];
    chart.update();
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

function getAjustesDataForDocente(docentes, docenteName) {
    if (docenteName === 'all') {
        const allAjustes = docentes.flatMap(d => d[2]);
        return groupAjustesByStatus(allAjustes);
    } else {
        const docente = docentes.find(d => d[0] === docenteName);
        const ajustes = docente ? docente[2] : [];
        return groupAjustesByStatus(ajustes);
    }
}

function getBankHoursDataForDocente(docentes, docenteName) {
    const docente = docentes.find(d => d[0] === docenteName);
    const bancoHoras = docente ? docente[3] : [];
    return groupBankHoursData(bancoHoras);
}

function getHoursWorkedDataForDocente(docentes, docenteName) {
    const docente = docentes.find(d => d[0] === docenteName);
    const marcacoes = docente ? docente[1] : [];
    return groupHoursWorkedByWeek(marcacoes);
}

async function initialize() {
    const docentes = await fetchData();

    if (Array.isArray(docentes) && docentes.length > 0) {
        populateSelect('lineChartSelect', docentes, true);
        populateSelect('barChartSelect', docentes, true);
        populateSelect('bankHoursChartSelect', docentes, false);
        populateSelect('hoursWorkedChartSelect', docentes, false);

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
                    data: [],
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

        const bankHoursCtx = document.getElementById('bankHoursChart').getContext('2d');
        const bankHoursChart = new Chart(bankHoursCtx, {
            type: 'pie',
            data: {
                labels: ['Saldo Atual', 'Saldo Restante'],
                datasets: [{
                    label: 'Saldo Atual do Banco de Horas',
                    data: [],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                layout: {
                    padding: {
                        left: 1,
                        right: 1,
                        top: 1,
                        bottom: 1
                    }
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                const value = tooltipItem.raw * 3600000;
                                const { hours, minutes } = convertMillisecondsToHoursAndMinutes(value);
                                return `${hours}h ${minutes}m`;
                            }
                        }
                    }
                }
            }
        });

        const hoursWorkedCtx = document.getElementById('hoursWorkedChart').getContext('2d');
        const hoursWorkedChart = new Chart(hoursWorkedCtx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Horas Trabalhadas por Semana',
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
                        title: { display: true, text: 'Semana' },
                        ticks: {
                            callback: function(value, index, values) {
                                const label = this.getLabelForValue(value);
                                return label.length > 10 ? label.slice(0, 10) + '...' : label;
                            }
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Horas' },
                        ticks: {
                            callback: function(value) {
                                const { hours, minutes } = convertMillisecondsToHoursAndMinutes(value * 3600000);
                                return `${hours}h ${minutes}m`;
                            }
                        }
                    }
                }
            }
        });

        document.getElementById('lineChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getDataForDocente(docentes, selectedDocente);
            updateChart(lineChart, data);
        });

        document.getElementById('barChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getAjustesDataForDocente(docentes, selectedDocente);
            updateChart(barChart, data);
        });

        document.getElementById('bankHoursChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getBankHoursDataForDocente(docentes, selectedDocente);
            if (data.length === 0 || data[0][1] <= 0) {
                document.getElementById('bankHoursChart').style.display = 'none';
                document.getElementById('noBankHoursMessage').style.display = 'block';
            } else {
                document.getElementById('bankHoursChart').style.display = 'block';
                document.getElementById('noBankHoursMessage').style.display = 'none';
                updatePieChart(bankHoursChart, data);
            }
        });

        document.getElementById('hoursWorkedChartSelect').addEventListener('change', (event) => {
            const selectedDocente = event.target.value;
            const data = getHoursWorkedDataForDocente(docentes, selectedDocente);
            updateChart(hoursWorkedChart, data, (value) => value);
        });

        // Renderizar gráficos para "Todos os Usuários" inicialmente, onde aplicável
        updateChart(lineChart, getDataForDocente(docentes, 'all'));
        updateChart(barChart, getAjustesDataForDocente(docentes, 'all'));

        // Renderizar gráfico de banco de horas para o primeiro usuário da lista
        const firstDocenteName = docentes[0][0];
        const bankHoursData = getBankHoursDataForDocente(docentes, firstDocenteName);
        if (bankHoursData.length === 0 || bankHoursData[0][1] <= 0) {
            document.getElementById('bankHoursChart').style.display = 'none';
            document.getElementById('noBankHoursMessage').style.display = 'block';
        } else {
            document.getElementById('bankHoursChart').style.display = 'block';
            document.getElementById('noBankHoursMessage').style.display = 'none';
            updatePieChart(bankHoursChart, bankHoursData);
        }
        document.getElementById('bankHoursChartSelect').value = firstDocenteName;
    } else {
        console.error('Dados dos docentes não foram carregados corretamente:', docentes);
    }
}

window.onload = initialize;
