package com.gleb.Finance.controllers;

import com.gleb.Finance.dao.UserDao;
import com.gleb.Finance.dto.*;
import com.gleb.Finance.models.User;
import com.gleb.Finance.services.DashBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashBoardService dashBoardService;
    private final UserDao userDao;

    public DashboardController(DashBoardService dashBoardService, UserDao userDao) {
        this.dashBoardService = dashBoardService;
        this.userDao = userDao;
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();
        System.out.println("Username from authentication: " + username);

        // Ищем по username, а не по email
        User user = userDao.findByUsername(username);

        if (user != null) {
            System.out.println("✅ User found: " + user.getUsername() + ", ID: " + user.getId());
            return user.getId();
        } else {
            System.out.println("❌ User not found with username: " + username);
            return null;
        }
    }

    @GetMapping("/financialSummary")
    public ResponseEntity<FinancialSummaryDto> getFinancialSummary() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            logger.warn("Unauthorized access attempt to financialSummary");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Getting financialSummary for user id: {}", userId);
        FinancialSummaryDto financialSummaryDto = dashBoardService.getFinancialSummaryDto(userId);
        logger.info("FinancialSummary retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(financialSummaryDto);
    }

    @GetMapping("/dashBoard")
    public ResponseEntity<FinancialDashboardDto> getDashboard() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            logger.warn("Unauthorized access attempt to dashboard");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Getting dashboard for user id: {}", userId);
        FinancialDashboardDto dashboard = dashBoardService.getFinanceDashboard(userId);
        logger.info("Dashboard retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/wallets")
    public ResponseEntity<WalletsPageDto> getWallets() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            logger.warn("Unauthorized access attempt to wallets");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Getting Wallets for user id: {}", userId);
        WalletsPageDto accountOverviewDto = dashBoardService.getWallets(userId);
        logger.info("Wallets retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(accountOverviewDto);
    }

    @GetMapping("/expenses")
    public ResponseEntity<ExpensePageDto> getExpenses() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            logger.warn("Unauthorized access attempt to expenses");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Getting Expenses for user id: {}", userId);
        ExpensePageDto expensePageDto = dashBoardService.getExpenses(userId);
        logger.info("Expenses retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(expensePageDto);
    }

    @GetMapping("/incomes")
    public ResponseEntity<IncomePageDto> getIncomes() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            logger.warn("Unauthorized access attempt to incomes");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Getting Incomes for user id: {}", userId);
        IncomePageDto incomePageDto = dashBoardService.getIncomes(userId);
        logger.info("Incomes retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(incomePageDto);
    }
}