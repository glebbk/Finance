document.addEventListener('DOMContentLoaded', () => {
    feather.replace();
    loadData();
});

const authModal = document.getElementById('auth-prompt');
const fmt = (v) => new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB', maximumFractionDigits: 0 }).format(v || 0);

async function loadData() {
    const creds = localStorage.getItem('user_auth');
    if (!creds) {
        authModal.classList.remove('hidden');
        return;
    }

    try {
        // Убедись, что путь "/" совпадает с твоим контроллером.
        // Если есть префикс, например /api, то пиши '/api/financialSummary'
        const response = await fetch('/financialSummary', {
            headers: { 'Authorization': `Basic ${creds}` }
        });

        if (response.status === 401) {
            localStorage.removeItem('user_auth');
            authModal.classList.remove('hidden');
            return;
        }

        const data = await response.json();

        // 1. Общий баланс
        document.getElementById('data-totalBalance').innerText = fmt(data.totalBalance);

        // 2. Доходы
        document.getElementById('data-totalIncomes').innerText = fmt(data.totalIncomes);

        // 3. Расходы
        document.getElementById('data-totalExpenses').innerText = fmt(data.totalExpenses);

        // 4. Сбережения
        document.getElementById('data-saving').innerText = fmt(data.saving);

        // ЛОГИКА ДЛЯ ЦЕЛИ (null check)
        const targetBlock = document.getElementById('target-section');
        const percentBadge = document.getElementById('data-percent');

        if (data.savingTarget === null || data.savingTarget === 0) {
            targetBlock.classList.add('hidden'); // Скрываем подпись цели
            percentBadge.classList.add('hidden'); // Скрываем процент
        } else {
            targetBlock.classList.remove('hidden');
            percentBadge.classList.remove('hidden');
            document.getElementById('data-savingTarget').innerText = fmt(data.savingTarget);

            const p = Math.round((data.saving / data.savingTarget) * 100);
            percentBadge.innerText = p + '%';
        }

        document.getElementById('user-display-name').innerText = localStorage.getItem('user_login');
        authModal.classList.add('hidden');
        feather.replace();

    } catch (err) {
        console.error("Ошибка подключения к бэкенду. Проверь URL в fetch().", err);
    }
}

// Форма входа
document.getElementById('login-form').addEventListener('submit', (e) => {
    e.preventDefault();
    const l = document.getElementById('auth-login').value;
    const p = document.getElementById('auth-password').value;
    localStorage.setItem('user_auth', btoa(`${l}:${p}`));
    localStorage.setItem('user_login', l);
    loadData();
});

function logout() {
    localStorage.clear();
    location.reload();
}