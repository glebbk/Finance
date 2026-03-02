const API_BASE = '/api/dashboard';

document.addEventListener('DOMContentLoaded', () => {
    feather.replace();
    loadDashboardData();
});

async function loadDashboardData() {
    const creds = localStorage.getItem('user_auth');
    if (!creds) {
        document.getElementById('auth-prompt').classList.remove('hidden');
        return;
    }

    const headers = { 'Authorization': `Basic ${creds}` };

    try {
        // Загружаем всё параллельно
        const [resSummary, resChart, resWallets] = await Promise.all([
            fetch(`${API_BASE}/financialSummary`, { headers }),
            fetch(`${API_BASE}/cashFlow`, { headers }),
            fetch(`${API_BASE}/walletsDistribution`, { headers })
        ]);

        if (resSummary.status === 401) return logout();

        const summary = await resSummary.json();
        const chartData = await resChart.json();
        const walletData = await resWallets.json();

        // Обновляем UI компоненты
        updateSummaryTiles(summary);
        renderCashFlowChart(chartData);
        renderWalletChart(walletData); // Новый вызов

        document.getElementById('user-display-name').innerText = localStorage.getItem('user_login');
        document.getElementById('auth-prompt').classList.add('hidden');
        feather.replace();

    } catch (err) {
        console.error("Критическая ошибка загрузки:", err);
    }
}

function updateSummaryTiles(data) {
    const fmt = (v) => new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB', maximumFractionDigits: 0 }).format(v || 0);

    document.getElementById('data-totalBalance').innerText = fmt(data.totalBalance);
    document.getElementById('data-totalIncomes').innerText = fmt(data.totalIncomes);
    document.getElementById('data-totalExpenses').innerText = fmt(data.totalExpenses);
    document.getElementById('data-saving').innerText = fmt(data.saving);

    const targetSec = document.getElementById('target-section');
    const badge = document.getElementById('target-percent');

    if (!data.savingTarget || data.savingTarget === 0) {
        targetSec.classList.add('hidden');
        badge.classList.add('hidden');
    } else {
        targetSec.classList.remove('hidden');
        badge.classList.remove('hidden');
        document.getElementById('data-savingTarget').innerText = fmt(data.savingTarget);
        const p = Math.round((data.saving / data.savingTarget) * 100);
        badge.innerText = p + '%';
    }
}

function logout() {
    localStorage.clear();
    location.reload();
}

// Форма входа (остается без изменений)
document.getElementById('login-form').addEventListener('submit', (e) => {
    e.preventDefault();
    const l = document.getElementById('auth-login').value;
    const p = document.getElementById('auth-password').value;
    localStorage.setItem('user_auth', btoa(`${l}:${p}`));
    localStorage.setItem('user_login', l);
    loadDashboardData();
});