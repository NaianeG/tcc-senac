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

function preencherSelect(selectId, docentes, incluirOpcaoTodos = false) {
    const select = document.getElementById(selectId);
    select.innerHTML = '';  // limpando opções previas
    if (incluirOpcaoTodos) {
        const opcaoTodos = document.createElement('option');
        opcaoTodos.value = 'all';
        opcaoTodos.text = 'Todos os Usuários';
        select.appendChild(opcaoTodos);
    }
    docentes.forEach(docente => {
        const option = document.createElement('option');
        option.value = docente[0];
        option.text = docente[0];
        select.appendChild(option);
    });
}

function agruparDadosPorMes(marcacoes) {
    const dadosAgrupados = {};
    marcacoes.forEach(m => {
        const data = new Date(m.data);
        const mes = data.getFullYear() + '-' + (data.getMonth() + 1).toString().padStart(2, '0');
        if (!dadosAgrupados[mes]) {
            dadosAgrupados[mes] = 0;
        }
        dadosAgrupados[mes] += 1;
    });

    const mesesNoIntervalo = obterMesesNoIntervalo(marcacoes);
    mesesNoIntervalo.forEach(mes => {
        if (!dadosAgrupados[mes]) {
            dadosAgrupados[mes] = 0;
        }
    });

    return Object.keys(dadosAgrupados)
        .sort((a, b) => new Date(a) - new Date(b))
        .map(mes => ({ x: new Date(mes), y: dadosAgrupados[mes] }));
}

function obterMesesNoIntervalo(marcacoes) {
    const datas = marcacoes.map(m => new Date(m.data));
    const dataMinima = new Date(Math.min.apply(null, datas));
    const dataMaxima = new Date(Math.max.apply(null, datas));

    const meses = [];
    const atual = new Date(dataMinima.getFullYear(), dataMinima.getMonth(), 1);

    while (atual <= dataMaxima) {
        const mes = atual.getFullYear() + '-' + (atual.getMonth() + 1).toString().padStart(2, '0');
        meses.push(mes);
        atual.setMonth(atual.getMonth() + 1);
    }

    return meses;
}

function agruparAjustesPorStatus(ajustes) {
    const contagemStatus = {
        pendentes: 0,
        aprovados: 0,
        rejeitados: 0
    };
    ajustes.forEach(a => {
        if (a.status === null) {
            contagemStatus.pendentes += 1;
        } else if (a.status === true) {
            contagemStatus.aprovados += 1;
        } else if (a.status === false) {
            contagemStatus.rejeitados += 1;
        }
    });
    return [contagemStatus.pendentes, contagemStatus.aprovados, contagemStatus.rejeitados];
}

function converterMilissegundosParaHorasEMinutos(ms) {
    console.log('Converter MS para Horas e Minutos:', ms);
    const totalMinutos = ms / 1000 / 60;
    const horas = Math.floor(totalMinutos / 60);
    const minutos = Math.floor(totalMinutos % 60);
    console.log(`Resultado: ${horas}h ${minutos}m`);
    return { horas, minutos, formatado: `${horas}h ${minutos}m` };
}

function agruparDadosBancoHoras(bancoHoras) {
    return bancoHoras.map(bh => {
        const saldoAtualHoras = bh.saldoAtual / 3600000; // Convertendo milissegundos para horas
        const saldoRestante = bh.saldoMensal - saldoAtualHoras;
        console.log('Saldo Atual:', saldoAtualHoras, 'Saldo Restante:', saldoRestante);
        return [saldoAtualHoras, saldoRestante > 0 ? saldoRestante : 0];
    });
}

function agruparHorasTrabalhadasPorSemana(marcacoes) {
    const dadosAgrupados = {};
    marcacoes.forEach(m => {
        const data = new Date(m.data);
        const numeroSemana = obterNumeroSemana(data);
        const rotuloSemana = `Semana ${numeroSemana}`;
        if (!dadosAgrupados[rotuloSemana]) {
            dadosAgrupados[rotuloSemana] = 0;
        }
        const entrada = new Date(`${data.toDateString()} ${m.horaEntrada}`);
        const saida = m.horaSaida ? new Date(`${data.toDateString()} ${m.horaSaida}`) : new Date();

        if (entrada && saida && saida > entrada) {
            dadosAgrupados[rotuloSemana] += (saida - entrada); // Calculando a diferença em milissegundos
        }
    });

    const rotulosSemanas = Object.keys(dadosAgrupados).sort((a, b) => {
        const semanaA = parseInt(a.split(' ')[1]);
        const semanaB = parseInt(b.split(' ')[1]);
        return semanaA - semanaB;
    });

    return rotulosSemanas.map((rotuloSemana, indice) => ({
        x: `Semana ${indice + 1}`, // semanas numeradas de 1 a 4
        y: dadosAgrupados[rotuloSemana]
    }));
}

function obterNumeroSemana(data) {
    const dataAtual = new Date(data.getFullYear(), data.getMonth(), data.getDate());
    const primeiroDiaDoMes = new Date(dataAtual.getFullYear(), dataAtual.getMonth(), 1);
    const diasPassadosDoMes = (dataAtual - primeiroDiaDoMes) / 86400000;
    return Math.ceil((diasPassadosDoMes + primeiroDiaDoMes.getDay() + 1) / 7);
}

function atualizarGrafico(grafico, dados, callbackRotuloX = null) {
    grafico.data.datasets[0].data = dados;
    if (callbackRotuloX) {
        grafico.options.scales.x.ticks.callback = callbackRotuloX;
    }
    grafico.update();
}

function atualizarGraficoPizza(grafico, dados) {
    grafico.data.datasets[0].data = dados[0];
    console.log('Atualizando gráfico de pizza com dados:', dados[0]);
    grafico.update();
}

function obterDadosPorDocente(docentes, nomeDocente) {
    if (nomeDocente === 'all') {
        const todasMarcacoes = docentes.flatMap(d => d[1]);
        return agruparDadosPorMes(todasMarcacoes);
    } else {
        const docente = docentes.find(d => d[0] === nomeDocente);
        const marcacoes = docente ? docente[1] : [];
        return agruparDadosPorMes(marcacoes);
    }
}

function obterAjustesPorDocente(docentes, nomeDocente) {
    if (nomeDocente === 'all') {
        const todosAjustes = docentes.flatMap(d => d[2]);
        return agruparAjustesPorStatus(todosAjustes);
    } else {
        const docente = docentes.find(d => d[0] === nomeDocente);
        const ajustes = docente ? docente[2] : [];
        return agruparAjustesPorStatus(ajustes);
    }
}

function obterDadosBancoHorasPorDocente(docentes, nomeDocente) {
    const docente = docentes.find(d => d[0] === nomeDocente);
    const bancoHoras = docente ? docente[3] : [];
    console.log('Dados banco de horas para:', nomeDocente, bancoHoras);
    return agruparDadosBancoHoras(bancoHoras);
}

function obterHorasTrabalhadasPorDocente(docentes, nomeDocente) {
    const docente = docentes.find(d => d[0] === nomeDocente);
    const marcacoes = docente ? docente[1] : [];
    const mesAtual = new Date().getMonth();
    const anoAtual = new Date().getFullYear();

    const marcacoesMesAtual = marcacoes.filter(m => {
        const data = new Date(m.data);
        return data.getMonth() === mesAtual && data.getFullYear() === anoAtual;
    });

    return agruparHorasTrabalhadasPorSemana(marcacoesMesAtual);
}

async function inicializar() {
    const docentes = await fetchData();

    if (Array.isArray(docentes) && docentes.length > 0) {
        preencherSelect('lineChartSelect', docentes, true);
        preencherSelect('barChartSelect', docentes, true);
        preencherSelect('bankHoursChartSelect', docentes, false);
        preencherSelect('hoursWorkedChartSelect', docentes, false);

        const ctxLinha = document.getElementById('lineChart').getContext('2d');
        const graficoLinha = new Chart(ctxLinha, {
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
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return `Qtd: ${tooltipItem.raw.y}`; // Mostra a quantidade de registros no tooltip
                            }
                        }
                    }
                }
            }
        });

        const ctxBarra = document.getElementById('barChart').getContext('2d');
        const graficoBarra = new Chart(ctxBarra, {
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

        const ctxBancoHoras = document.getElementById('bankHoursChart').getContext('2d');
        const graficoBancoHoras = new Chart(ctxBancoHoras, {
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
                                const valor = tooltipItem.raw; // Valor original
                                const { horas, minutos } = converterMilissegundosParaHorasEMinutos(valor * 3600000); // Convertendo para milissegundos
                                return `${horas}h ${minutos}m`;
                            }
                        }
                    }
                }
            }
        });

        const ctxHorasTrabalhadas = document.getElementById('hoursWorkedChart').getContext('2d');
        const graficoHorasTrabalhadas = new Chart(ctxHorasTrabalhadas, {
            type: 'line',
            data: {
                labels: ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'], // Rótulos das semanas
                datasets: [{
                    label: 'Horas Trabalhadas',
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
                            callback: function(value, index) {
                                return `Semana ${index + 1}`;
                            }
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Horas' },
                        ticks: {
                            callback: function(value) {
                                const { horas, minutos } = converterMilissegundosParaHorasEMinutos(value); // Certificando-se que o valor esteja correto
                                return `${horas}h ${minutos}m`;
                            }
                        }
                    }
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                console.log('Tooltip Item:', tooltipItem);
                                const valor = tooltipItem.raw.y; // Usar valor correto diretamente
                                console.log('Valor:', valor);
                                const { horas, minutos } = converterMilissegundosParaHorasEMinutos(valor);
                                return `${horas}h ${minutos}m`;
                            }
                        }
                    }
                }
            }
        });

        document.getElementById('lineChartSelect').addEventListener('change', (event) => {
            const docenteSelecionado = event.target.value;
            const dados = obterDadosPorDocente(docentes, docenteSelecionado);
            atualizarGrafico(graficoLinha, dados);
        });

        document.getElementById('barChartSelect').addEventListener('change', (event) => {
            const docenteSelecionado = event.target.value;
            const dados = obterAjustesPorDocente(docentes, docenteSelecionado);
            atualizarGrafico(graficoBarra, dados);
        });

        document.getElementById('bankHoursChartSelect').addEventListener('change', (event) => {
            const docenteSelecionado = event.target.value;
            const dados = obterDadosBancoHorasPorDocente(docentes, docenteSelecionado);
            console.log('Dados para o gráfico de pizza:', dados);
            if (dados.length === 0 || (dados[0][0] === 0 && dados[0][1] === 0)) {
                document.getElementById('bankHoursChart').style.display = 'none';
                document.getElementById('noBankHoursMessage').style.display = 'block';
            } else {
                document.getElementById('bankHoursChart').style.display = 'block';
                document.getElementById('noBankHoursMessage').style.display = 'none';
                atualizarGraficoPizza(graficoBancoHoras, dados);
            }
        });

        document.getElementById('hoursWorkedChartSelect').addEventListener('change', (event) => {
            const docenteSelecionado = event.target.value;
            const dados = obterHorasTrabalhadasPorDocente(docentes, docenteSelecionado);
            atualizarGrafico(graficoHorasTrabalhadas, dados);
        });

        // Renderizar gráficos para "Todos os Usuários" inicialmente, onde aplicável
        atualizarGrafico(graficoLinha, obterDadosPorDocente(docentes, 'all'));
        atualizarGrafico(graficoBarra, obterAjustesPorDocente(docentes, 'all'));

        // Renderizar gráfico de banco de horas para o primeiro usuário da lista
        const primeiroDocenteNome = docentes[0][0];
        const dadosBancoHoras = obterDadosBancoHorasPorDocente(docentes, primeiroDocenteNome);
        console.log('Dados banco de horas para o primeiro docente:', primeiroDocenteNome, dadosBancoHoras);
        if (dadosBancoHoras.length === 0 || (dadosBancoHoras[0][0] === 0 && dadosBancoHoras[0][1] === 0)) {
            document.getElementById('bankHoursChart').style.display = 'none';
            document.getElementById('noBankHoursMessage').style.display = 'block';
        } else {
            document.getElementById('bankHoursChart').style.display = 'block';
            document.getElementById('noBankHoursMessage').style.display = 'none';
            atualizarGraficoPizza(graficoBancoHoras, dadosBancoHoras);
        }
        document.getElementById('bankHoursChartSelect').value = primeiroDocenteNome;

        // Renderizar gráfico de horas trabalhadas para o primeiro usuário da lista
        const dadosHorasTrabalhadas = obterHorasTrabalhadasPorDocente(docentes, primeiroDocenteNome);
        console.log('Dados horas trabalhadas para o primeiro docente:', primeiroDocenteNome, dadosHorasTrabalhadas);
        atualizarGrafico(graficoHorasTrabalhadas, dadosHorasTrabalhadas);
        document.getElementById('hoursWorkedChartSelect').value = primeiroDocenteNome;
    } else {
        console.error('Dados dos docentes não foram carregados corretamente:', docentes);
    }
}

window.onload = inicializar;
