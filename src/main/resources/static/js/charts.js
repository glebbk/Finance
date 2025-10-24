// js/charts.js
let expenseChartInstance = null;
let cashFlowChartInstance = null;

// Уничтожение графиков
function destroyCharts() {
    if (expenseChartInstance) {
        expenseChartInstance.destroy();
        expenseChartInstance = null;
    }
    if (cashFlowChartInstance) {
        cashFlowChartInstance.destroy();
        cashFlowChartInstance = null;
    }
}

// Отрисовка графиков
function renderCharts(data) {
    console.log('Rendering charts with data:', data);
    destroyCharts();
    initializeEmptyCharts();
    console.log('Charts rendered successfully');
}

function initializeEmptyCharts() {
    console.log('Initializing empty charts');
    // Инициализация пустого графика расходов
    const expenseCtx = document.getElementById('expenseChart');
    if (expenseCtx) {
        expenseCtx.getContext('2d').clearRect(0, 0, expenseCtx.width, expenseCtx.height);

        expenseChartInstance = new Chart(expenseCtx, {
            type: 'doughnut',
            data: {
                labels: ['Нет данных'],
                datasets: [{
                    data: [100],
                    backgroundColor: ['#e5e7eb'],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        enabled: false
                    }
                }
            }
        });
    }

    // Инициализация пустого графика денежных потоков
    const cashFlowCtx = document.getElementById('cashFlowChart');
    if (cashFlowCtx) {
        cashFlowCtx.getContext('2d').clearRect(0, 0, cashFlowCtx.width, cashFlowCtx.height);

        cashFlowChartInstance = new Chart(cashFlowCtx, {
            type: 'line',
            data: {
                labels: [],
                datasets: []
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
    console.log('Empty charts initialized successfully');
}