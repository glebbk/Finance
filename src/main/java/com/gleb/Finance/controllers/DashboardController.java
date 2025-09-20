package com.gleb.Finance.controllers;

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
        logger.debug("Dashboard retrieved successfully for user id: {}", userId);

        return ResponseEntity.ok(dashboard);
    }
}