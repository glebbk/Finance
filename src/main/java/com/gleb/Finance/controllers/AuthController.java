package com.gleb.Finance.controllers;

import com.gleb.Finance.dao.UserDao;
import com.gleb.Finance.dto.RegisterRequest;
import com.gleb.Finance.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("Registration attempt for: {}", registerRequest.getEmail());

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
            logger.info("User registered successfully: {}", savedUser.getUsername());

            return ResponseEntity.ok("Пользователь успешно зарегистрирован");

        } catch (Exception e) {
            logger.info("Registration error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при регистрации: " + e.getMessage());
        }
    }
}