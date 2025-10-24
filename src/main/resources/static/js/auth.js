// js/auth.js
// Показать окно авторизации
function showAuthPrompt() {
    console.log('showAuthPrompt called');
    const authPrompt = document.getElementById('auth-prompt');
    if (authPrompt) {
        authPrompt.classList.remove('hidden');
        console.log('Auth prompt should be visible now');
    }
}

// Скрыть окно авторизации
function hideAuthPrompt() {
    console.log('hideAuthPrompt called');
    const authPrompt = document.getElementById('auth-prompt');
    if (authPrompt) {
        authPrompt.classList.add('hidden');
        console.log('Auth prompt should be hidden now');
    }
    document.getElementById('auth-email').value = '';
    document.getElementById('auth-password').value = '';
    hideError();
}

// Обновление статуса авторизации
function updateAuthStatus() {
    const username = localStorage.getItem('username');
    const authStatus = document.getElementById('auth-status-text');
    const userEmail = document.getElementById('user-email');
    const userName = document.getElementById('user-name');

    if (username) {
        authStatus.textContent = 'Авторизован';
        userEmail.textContent = username;
        userName.textContent = 'Пользователь';
        document.getElementById('auth-status').classList.remove('text-gray-600');
        document.getElementById('auth-status').classList.add('text-success-600');
    } else {
        authStatus.textContent = 'Не авторизован';
        userEmail.textContent = 'Войдите в систему';
        userName.textContent = 'Гость';
        document.getElementById('auth-status').classList.remove('text-success-600');
        document.getElementById('auth-status').classList.add('text-gray-600');
    }
}

// Функция для аутентифицированных запросов
async function makeAuthenticatedRequest(url, options = {}) {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    console.log('Making authenticated request to:', url);

    if (!username || !password) {
        throw new Error('Требуется авторизация');
    }

    const headers = {
        'Authorization': 'Basic ' + btoa(username + ':' + password),
        'Content-Type': 'application/json',
        ...options.headers
    };

    console.log('Sending request with Basic Auth header');
    const response = await fetch(url, { ...options, headers });

    console.log('Response status:', response.status);

    if (response.status === 401) {
        throw new Error('Неверные учетные данные');
    }

    if (!response.ok) {
        throw new Error('Ошибка сервера: ' + response.status);
    }

    const responseData = await response.json();
    console.log('Response data received successfully');
    return responseData;
}

// Выход из системы
function logout() {
    console.log('Logout called');
    destroyCharts();
    localStorage.removeItem('username');
    localStorage.removeItem('password');
    updateAuthStatus();
    location.reload();
}