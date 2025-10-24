// js/utils.js
const API_BASE_URL = 'http://localhost:8080/api';

// Форматирование денег
function formatMoney(amount) {
    if (!amount && amount !== 0) return '0 ₽';
    return new Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        minimumFractionDigits: 0
    }).format(amount);
}

// Форматирование даты
function formatDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diffTime = Math.abs(now - date);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays === 1) return 'Сегодня';
    if (diffDays === 2) return 'Вчера';
    if (diffDays <= 7) return `${diffDays - 1} дней назад`;

    return date.toLocaleDateString('ru-RU', {
        day: 'numeric',
        month: 'long'
    });
}

// Обновление текущей даты
function updateCurrentDate() {
    const now = new Date();
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    document.getElementById('current-date').textContent = `Сегодня: ${now.toLocaleDateString('ru-RU', options)}`;
}

// Показать/скрыть загрузку
function showLoading(show) {
    const loadingElement = document.getElementById('loading');
    if (loadingElement) {
        loadingElement.classList.toggle('show', show);
    }
}

// Показать ошибку
function showError(message) {
    const errorElement = document.getElementById('error');
    const errorMessage = document.getElementById('error-message');
    if (errorElement && errorMessage) {
        errorMessage.textContent = message;
        errorElement.classList.add('show');
    }
}

// Скрыть ошибку
function hideError() {
    const errorElement = document.getElementById('error');
    if (errorElement) {
        errorElement.classList.remove('show');
    }
}