let cashFlowChartInstance = null;
let walletPieChartInstance = null;

// Линейный график (оставляем как был)
function renderCashFlowChart(data) {
    const ctx = document.getElementById('cashFlowChart').getContext('2d');
    if (cashFlowChartInstance) cashFlowChartInstance.destroy();

    cashFlowChartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map(d => {
                const date = new Date(d.date);
                return date.toLocaleDateString('ru-RU', { day: 'numeric', month: 'short' });
            }),
            datasets: [
                {
                    label: 'Доходы',
                    data: data.map(d => d.incomes),
                    borderColor: '#10b981',
                    backgroundColor: 'rgba(16, 185, 129, 0.05)',
                    fill: true,
                    tension: 0.4,
                    borderWidth: 3
                },
                {
                    label: 'Расходы',
                    data: data.map(d => d.expenses),
                    borderColor: '#f43f5e',
                    backgroundColor: 'rgba(244, 63, 94, 0.05)',
                    fill: true,
                    tension: 0.4,
                    borderWidth: 3
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, ticks: { callback: v => v.toLocaleString() + ' ₽' } },
                x: { grid: { display: false } }
            }
        }
    });
}

function renderWalletChart(data) {
    const ctx = document.getElementById('walletPieChart').getContext('2d');
    if (walletPieChartInstance) walletPieChartInstance.destroy();

    const colors = ['#3b82f6', '#10b981', '#f59e0b', '#8b5cf6', '#f43f5e', '#06b6d4', '#ec4899', '#64748b'];

    walletPieChartInstance = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: data.map(d => d.label),
            datasets: [{
                data: data.map(d => d.amount),
                backgroundColor: colors,
                hoverOffset: 15,
                borderWidth: 4,
                borderColor: '#ffffff'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '70%',
            plugins: {
                legend: {
                    position: 'right', // Переносим направо
                    align: 'center',   // Центрируем по вертикали
                    labels: {
                        boxWidth: 12,
                        padding: 15,    // Расстояние между строками
                        font: { size: 12, weight: '600' },
                        usePointStyle: true // Сделать маркеры круглыми (опционально)
                    }
                },
                tooltip: {
                    padding: 12,
                    callbacks: {
                        label: (ctx) => ` ${ctx.label}: ${ctx.raw.toLocaleString()} ₽`
                    }
                }
            }
        }
    });
}