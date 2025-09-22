package com.gleb.Finance.controllers;

import com.gleb.Finance.dto.ExpensePageDto;
import com.gleb.Finance.dto.IncomePageDto;
import com.gleb.Finance.dto.WalletsPageDto;
import com.gleb.Finance.dto.FinancialDashboardDto;
import com.gleb.Finance.services.DashBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashBoardService dashBoardService;

    public DashboardController(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FinancialDashboardDto> getDashboard(
            @PathVariable Long userId) {

        logger.info("Getting dashboard for user id: {}", userId);
        FinancialDashboardDto dashboard = dashBoardService.getFinanceDashboard(userId);
        logger.info("Dashboard retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/wallets/{userId}")
    public ResponseEntity<WalletsPageDto> getWallets(
            @PathVariable Long userId) {

        logger.info("Getting Wallets for user id: {}", userId);
        WalletsPageDto accountOverviewDto = dashBoardService.getWallets(userId);
        logger.info("Wallets retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(accountOverviewDto);
    }

    @GetMapping("/expenses/{userId}")
    public ResponseEntity<ExpensePageDto> getExpenses(
            @PathVariable Long userId) {

        logger.info("Getting Expenses for user id: {}", userId);
        ExpensePageDto expensePageDto = dashBoardService.getExpenses(userId);
        logger.info("Expenses retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(expensePageDto);
    }

    @GetMapping("/incomes/{userId}")
    public ResponseEntity<IncomePageDto> getIncomes(
            @PathVariable long userId) {

        logger.info("Getting Incomes for user id: {}", userId);
        IncomePageDto incomePageDto = dashBoardService.getIncomes(userId);
        logger.info("Incomes retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(incomePageDto);
    }
}