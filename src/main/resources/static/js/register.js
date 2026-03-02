//// js/register.js - БАЗОВАЯ ВЕРСИЯ ДЛЯ ТЕСТА
//function showRegisterPrompt() {
//    const username = prompt("Введите имя пользователя:");
//    const email = prompt("Введите email:");
//    const password = prompt("Введите пароль:");
//
//    if (username && email && password) {
//        registerUser(username, email, password);
//    }
//}
//
//async function registerUser(username, email, password) {
//    try {
//        console.log('Registering user:', { username, email });
//
//        const response = await fetch('http://localhost:8080/api/auth/register', {
//            method: 'POST',
//            headers: {
//                'Content-Type': 'application/json',
//            },
//            body: JSON.stringify({
//                username: username,
//                email: email,
//                password: password
//            })
//        });
//
//        if (response.ok) {
//            const result = await response.text();
//            alert('✅ ' + result);
//            console.log('Registration successful');
//        } else {
//            const error = await response.text();
//            alert('❌ ' + error);
//            console.error('Registration failed:', error);
//        }
//    } catch (error) {
//        console.error('Registration error:', error);
//        alert('❌ Ошибка сети: ' + error.message);
//    }
//}