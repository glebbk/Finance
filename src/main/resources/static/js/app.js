// js/app.js
// Функция для проверки аутентификации и загрузки данных
async function testAuthAndLoadData() {
    try {
        console.log('=== START testAuthAndLoadData ===');
        showLoading(true);
        hideError();

        const username = localStorage.getItem('username');
        const password = localStorage.getItem('password');
        console.log('Credentials from localStorage:', { username, password: password ? '***' : 'empty' });

        // Пробуем сделать простой запрос для проверки аутентификации
        console.log('Making test request to financialSummary...');
        const testData = await makeAuthenticatedRequest(`${API_BASE_URL}/dashboard/financialSummary`);
        console.log('✅ Authentication successful, received data:', testData);

        // Если успешно - загружаем все данные
        console.log('Loading financial summary...');
        await loadFinancialSummary();
        console.log('✅ Financial summary loaded');

        console.log('Loading dashboard...');
        await loadDashboard();
        console.log('✅ Dashboard loaded');

        // Скрываем окно авторизации после успешного входа
        console.log('Calling hideAuthPrompt...');
        hideAuthPrompt();
        showError(''); // Очищаем возможные предыдущие ошибки
        console.log('✅ Authentication flow completed successfully');

    } catch (error) {
        console.error('❌ Authentication failed:', error);
        // Если аутентификация не удалась
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        updateAuthStatus();

        if (error.message.includes('401') || error.message.includes('Неверные учетные данные')) {
            showError('Неверный email или пароль');
            showAuthPrompt(); // Показываем окно входа снова
        } else {
            showError('Ошибка авторизации: ' + error.message);
        }
    } finally {
        showLoading(false);
        console.log('=== END testAuthAndLoadData ===');
    }
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM Content Loaded');
    feather.replace();
    AOS.init();
    updateCurrentDate();
    updateAuthStatus();

    // Обработчик для кнопки отмены
    const cancelButton = document.getElementById('cancel-auth-btn');
    if (cancelButton) {
        cancelButton.addEventListener('click', hideAuthPrompt);
    }

    // Обработчик для клика вне модального окна
    const authPrompt = document.getElementById('auth-prompt');
    if (authPrompt) {
        authPrompt.addEventListener('click', function(e) {
            if (e.target === authPrompt) {
                hideAuthPrompt();
            }
        });
    }

    // Обработчик формы входа
    document.getElementById('login-form').addEventListener('submit', async function(e) {
        e.preventDefault();
        console.log('Login form submitted');
        const email = document.getElementById('auth-email').value;
        const password = document.getElementById('auth-password').value;

        if (!email || !password) {
            showError('Пожалуйста, заполните все поля');
            return;
        }

        console.log('Saving credentials to localStorage');
        // Сохраняем учетные данные
        localStorage.setItem('username', email);
        localStorage.setItem('password', password);

        updateAuthStatus();

        // Пробуем загрузить данные (функция сама закроет окно при успехе)
        await testAuthAndLoadData();
    });

    // Проверяем, есть ли сохраненные учетные данные
    const username = localStorage.getItem('username');
    if (username) {
        console.log('Found saved credentials, auto-login');
        // Автоматически загружаем данные если пользователь уже авторизован
        testAuthAndLoadData();
    } else {
        console.log('No saved credentials, showing auth prompt');
        // Показываем окно входа через небольшую задержку для лучшего UX
        setTimeout(() => {
            showAuthPrompt();
        }, 500);
    }
});