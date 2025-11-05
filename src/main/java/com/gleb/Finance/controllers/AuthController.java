// AuthController.java - ПОЛНАЯ ВЕРСИЯ
package com.gleb.Finance.controllers;

import com.gleb.Finance.dao.UserDao;
import com.gleb.Finance.dto.RegisterRequest;
import com.gleb.Finance.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/status")
    public ResponseEntity<String> authStatus() {
        return ResponseEntity.ok("Auth endpoint is working");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println("Registration attempt for: " + registerRequest.getEmail());

            // Проверяем, существует ли пользователь с таким email
            if (userDao.findByEmail(registerRequest.getEmail()) != null) {
                return ResponseEntity.badRequest()
                        .body("Пользователь с таким email уже существует");
            }

            if (userDao.findByUsername(registerRequest.getUsername()) != null) {
                return ResponseEntity.badRequest()
                        .body("Пользователь с таким именем уже существует");
            }

            User user = new User(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword())
            );

            User savedUser = userDao.save(user);
            System.out.println("User registered successfully: " + savedUser.getUsername());

            return ResponseEntity.ok("Пользователь успешно зарегистрирован");

        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при регистрации: " + e.getMessage());
        }
    }
}