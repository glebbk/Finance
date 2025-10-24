// js/dashboard.js
// Загрузка финансовой сводки
async function loadFinancialSummary() {
    try {
        console.log('Loading financial summary from API...');
        const data = await makeAuthenticatedRequest(`${API_BASE_URL}/dashboard/financialSummary`);
        renderFinancialCards(data);
        console.log('Financial summary rendered');
    } catch (error) {
        console.error('Error loading financial summary:', error);
        throw error;
    }
}

// Загрузка дашборда
async function loadDashboard() {
    try {
        console.log('Loading dashboard from API...');
        const data = await makeAuthenticatedRequest(`${API_BASE_URL}/dashboard/dashBoard`);
        renderDashboard(data);
        console.log('Dashboard rendered');
    } catch (error) {
        console.error('Error loading dashboard:', error);
        throw error;
    }
}

// Отрисовка финансовых карточек
function renderFinancialCards(data) {
    console.log('Rendering financial cards with data:', data);
    const container = document.getElementById('financial-cards');
    if (!container) {
        console.error('Financial cards container not found!');
        return;
    }

    function formatPercent(percent) {
        if (!percent) return '0%';
        const sign = percent >= 0 ? '+' : '';
        return `${sign}${Math.abs(percent)}%`;
    }

    function getChangeStyles(change) {
        if (!change) return { color: 'text-gray-500', icon: 'minus', sign: '' };

        const isPositive = change > 0;
        const color = isPositive ? 'text-success-500' : 'text-danger-500';
        const icon = isPositive ? 'trending-up' : 'trending-down';
        const sign = isPositive ? '+' : '';

        return { color, icon, sign };
    }

    const balanceStyles = getChangeStyles(data.balanceChange);
    const incomeStyles = getChangeStyles(data.incomesChange);
    const expenseStyles = getChangeStyles(data.expensesChange);

    container.innerHTML = `
    <div class="dashboard-card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <div class="flex items-center justify-between mb-4">
            <h3 class="text-gray-500 font-medium">Общий баланс</h3>
            <i data-feather="dollar-sign" class="text-primary-500"></i>
        </div>
        <p class="text-2xl font-bold">${formatMoney(data.totalBalance)}</p>
        <p class="text-sm ${balanceStyles.color} flex items-center mt-1">
            <i data-feather="${balanceStyles.icon}" class="w-4 h-4 mr-1"></i>
            <span>${balanceStyles.sign}${formatMoney(data.balanceChange)} (${formatPercent(data.balanceChangePercent)})</span>
        </p>
    </div>

    <div class="dashboard-card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <div class="flex items-center justify-between mb-4">
            <h3 class="text-gray-500 font-medium">Доходы</h3>
            <i data-feather="arrow-down-right" class="text-success-500"></i>
        </div>
        <p class="text-2xl font-bold">${formatMoney(data.totalIncomes)}</p>
        <p class="text-sm ${incomeStyles.color} flex items-center mt-1">
            <i data-feather="${incomeStyles.icon}" class="w-4 h-4 mr-1"></i>
            <span>${incomeStyles.sign}${formatMoney(data.incomesChange)} (${formatPercent(data.incomesChangePercent)})</span>
        </p>
        <p class="text-xs text-gray-500 mt-1">${data.incomesPeriod || 'за текущий месяц'}</p>
    </div>

    <div class="dashboard-card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <div class="flex items-center justify-between mb-4">
            <h3 class="text-gray-500 font-medium">Расходы</h3>
            <i data-feather="arrow-up-right" class="text-danger-500"></i>
        </div>
        <p class="text-2xl font-bold">${formatMoney(data.totalExpenses)}</p>
        <p class="text-sm ${expenseStyles.color} flex items-center mt-1">
            <i data-feather="${expenseStyles.icon}" class="w-4 h-4 mr-1"></i>
            <span>${expenseStyles.sign}${formatMoney(data.expensesChange)} (${formatPercent(data.expensesChangePercent)})</span>
        </p>
        <p class="text-xs text-gray-500 mt-1">${data.expensesTargetInfo || ''}</p>
    </div>

    <div class="dashboard-card bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <div class="flex items-center justify-between mb-4">
            <h3 class="text-gray-500 font-medium">Сбережения</h3>
            <i data-feather="save" class="text-primary-500"></i>
        </div>
        <p class="text-2xl font-bold">${formatMoney(data.saving)}</p>
        <p class="text-sm text-gray-500 mt-1">до цели: ${formatMoney(data.savingTarget)}</p>
        ${data.savingProgress && data.savingProgress !== 0 ?
            `<div class="mt-2 bg-gray-200 rounded-full h-2">
                <div class="bg-primary-500 h-2 rounded-full" style="width: ${Math.min(100, Math.max(0, data.savingProgress))}%"></div>
            </div>` : ''
        }
    </div>
`;

    feather.replace();
    console.log('Financial cards rendered successfully');
}

// Отрисовка дашборда
function renderDashboard(data) {
    console.log('Rendering dashboard with data:', data);
    document.getElementById('user-name').textContent = data.userName || 'Пользователь';
    document.getElementById('welcome-message').textContent = `Добро пожаловать, ${data.userName || 'Гость'}!`;

    renderRecentTransactions(data.recentTransactionDtoList);
    renderAccountsOverview(data.walletBalanceDtoList);
    renderCharts(data);
    console.log('Dashboard rendered successfully');
}

// Отрисовка последних транзакций
function renderRecentTransactions(transactions) {
    console.log('Rendering recent transactions:', transactions);
    const container = document.getElementById('recent-transactions');
    if (!container) {
        console.error('Recent transactions container not found!');
        return;
    }

    if (!transactions || transactions.length === 0) {
        container.innerHTML = '<p class="text-gray-500 text-center py-4">Нет операций</p>';
        return;
    }

    const transactionsHtml = transactions.slice(0, 6).map(transaction => {
        const isIncome = transaction.type === 'INCOME';
        const icon = isIncome ? 'dollar-sign' : 'shopping-bag';
        const colorClass = isIncome ? 'text-success-500' : 'text-danger-500';
        const bgColorClass = isIncome ? 'bg-success-50' : 'bg-danger-50';
        const amountSign = isIncome ? '+' : '-';

        return `
            <div class="transaction-item flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 cursor-pointer">
                <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-full ${bgColorClass} flex items-center justify-center">
                        <i data-feather="${icon}" class="${colorClass}"></i>
                    </div>
                    <div>
                        <p class="font-medium">${transaction.name}</p>
                        <p class="text-sm text-gray-500">${isIncome ? 'Доход' : 'Расход'}</p>
                    </div>
                </div>
                <div class="text-right">
                    <p class="font-medium ${colorClass}">${amountSign}${formatMoney(Math.abs(transaction.amount))}</p>
                    <p class="text-sm text-gray-500">${formatDate(transaction.dateTime)}</p>
                </div>
            </div>
        `;
    }).join('');

    container.innerHTML = transactionsHtml;
    feather.replace();
    console.log('Recent transactions rendered successfully');
}

// Отрисовка обзора счетов
function renderAccountsOverview(wallets) {
    console.log('Rendering accounts overview:', wallets);
    const container = document.getElementById('accounts-overview');
    if (!container) {
        console.error('Accounts overview container not found!');
        return;
    }

    if (!wallets || wallets.length === 0) {
        container.innerHTML = '<p class="text-gray-500 text-center py-4">Нет счетов</p>';
        return;
    }

    const walletsHtml = wallets.slice(0, 4).map(wallet => {
        const isNegative = wallet.balance < 0;
        const colorClass = isNegative ? 'text-danger-500' : 'text-gray-900';
        const bgColorClass = isNegative ? 'bg-danger-50' : 'bg-primary-50';
        const iconColorClass = isNegative ? 'text-danger-500' : 'text-primary-500';

        return `
            <div class="transaction-item flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 cursor-pointer">
                <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-full ${bgColorClass} flex items-center justify-center">
                        <i data-feather="credit-card" class="${iconColorClass}"></i>
                    </div>
                    <div>
                        <p class="font-medium">${wallet.name}</p>
                        <p class="text-sm text-gray-500">Счет</p>
                    </div>
                </div>
                <div class="text-right">
                    <p class="font-medium ${colorClass}">${formatMoney(wallet.balance)}</p>
                </div>
            </div>
        `;
    }).join('');

    container.innerHTML = walletsHtml;
    feather.replace();
    console.log('Accounts overview rendered successfully');
}

// Обновление данных
function refreshData() {
    console.log('Refresh data called');
    const username = localStorage.getItem('username');
    if (username) {
        loadFinancialSummary();
        loadDashboard();
    } else {
        showAuthPrompt();
    }
}