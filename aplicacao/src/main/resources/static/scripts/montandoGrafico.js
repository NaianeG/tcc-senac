// Dados fictícios
const lineChartData = {
    labels: ['2024-01', '2024-02', '2024-03', '2024-04', '2024-05', '2024-06'],
    datasets: [{
        label: 'Registros de Pontos por Mês',
        data: [50, 75, 60, 90, 80, 100],
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
    }]
};

const barChartData = {
    labels: ['Pendentes', 'Aprovados', 'Rejeitados'],
    datasets: [{
        label: 'Ajustes',
        data: [12, 19, 3],
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
};

const pieChartData = {
    labels: ['Admin', 'User'],
    datasets: [{
        label: 'Distribuição de Usuários',
        data: [2, 8],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)'
        ],
        borderWidth: 1
    }]
};

const hoursComparisonChartData = {
    labels: ['Docente 1', 'Docente 2', 'Docente 3'],
    datasets: [
        {
            label: 'Horas Trabalhadas',
            data: [160, 150, 170],
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
        },
        {
            label: 'Horas Previstas',
            data: [180, 180, 180],
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
            borderColor: 'rgba(255, 206, 86, 1)',
            borderWidth: 1
        }
    ]
};

const bankHoursChartData = {
    labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho'],
    datasets: [{
        label: 'Banco de Horas',
        data: [10, -5, 20, 15, -10, 5],
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
    }]
};

// Configurações dos gráficos
const lineChartConfig = {
    type: 'line',
    data: lineChartData,
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                beginAtZero: true
            },
            y: {
                beginAtZero: true
            }
        }
    }
};

const barChartConfig = {
    type: 'bar',
    data: barChartData,
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};

const pieChartConfig = {
    type: 'pie',
    data: pieChartData,
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
};

const hoursComparisonChartConfig = {
    type: 'bar',
    data: hoursComparisonChartData,
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};

const bankHoursChartConfig = {
    type: 'line',
    data: bankHoursChartData,
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                beginAtZero: true
            },
            y: {
                beginAtZero: true
            }
        }
    }
};

// Renderização dos gráficos
window.onload = function() {
    const lineCtx = document.getElementById('lineChart').getContext('2d');
    new Chart(lineCtx, lineChartConfig);

    const barCtx = document.getElementById('barChart').getContext('2d');
    new Chart(barCtx, barChartConfig);

    const pieCtx = document.getElementById('pieChart').getContext('2d');
    new Chart(pieCtx, pieChartConfig);

    const hoursComparisonCtx = document.getElementById('hoursComparisonChart').getContext('2d');
    new Chart(hoursComparisonCtx, hoursComparisonChartConfig);

    const bankHoursCtx = document.getElementById('bankHoursChart').getContext('2d');
    new Chart(bankHoursCtx, bankHoursChartConfig);
};
