package com.gleb.Finance.controllers;

import com.gleb.Finance.dao.UserDao;
import com.gleb.Finance.dto.FinancialSummaryDto;
import com.gleb.Finance.models.User;
import com.gleb.Finance.services.DashBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashBoardService dashBoardService;

    @MockitoBean
    private UserDao userDao;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
    }

    @Test
    @WithMockUser(username = "testuser")
    void getFinancialSummary_ShouldReturnFinancialSummary_WhenUserIsAuthenticated() throws Exception {
        // Arrange
        FinancialSummaryDto financialSummaryDto = new FinancialSummaryDto(
                BigDecimal.valueOf(15000.0),  // totalBalance
                BigDecimal.valueOf(1000.0),   // balanceChange
                BigDecimal.valueOf(7.14),     // balanceChangePercent
                BigDecimal.valueOf(5000.0),   // totalIncomes
                BigDecimal.valueOf(500.0),    // incomesChange
                BigDecimal.valueOf(11.11),    // incomesChangePercent
                BigDecimal.valueOf(3000.0),   // totalExpenses
                BigDecimal.valueOf(300.0),    // expensesChange
                BigDecimal.valueOf(11.11),    // expensesChangePercent
                BigDecimal.valueOf(5000.0),   // saving
                BigDecimal.valueOf(50.0),     // savingProgress
                BigDecimal.valueOf(10000.0)   // savingTarget
        );

        when(userDao.findByUsername("testuser")).thenReturn(testUser);
        when(dashBoardService.getFinancialSummaryDto(1L)).thenReturn(financialSummaryDto);

        // Act & Assert - используем правильные имена полей из DTO
        mockMvc.perform(get("/api/dashboard/financialSummary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBalance").value(15000.0))
                .andExpect(jsonPath("$.balanceChange").value(1000.0))
                .andExpect(jsonPath("$.balanceChangePercent").value(7.14))
                .andExpect(jsonPath("$.totalIncomes").value(5000.0))
                .andExpect(jsonPath("$.incomesChange").value(500.0))
                .andExpect(jsonPath("$.incomesChangePercent").value(11.11))
                .andExpect(jsonPath("$.totalExpenses").value(3000.0))
                .andExpect(jsonPath("$.expensesChange").value(300.0))
                .andExpect(jsonPath("$.expensesChangePercent").value(11.11))
                .andExpect(jsonPath("$.saving").value(5000.0))
                .andExpect(jsonPath("$.savingProgress").value(50.0))
                .andExpect(jsonPath("$.savingTarget").value(10000.0));
    }

    @Test
    @WithMockUser(username = "nonexistent")
    void getFinancialSummary_ShouldReturnUnauthorized_WhenUserNotFound() throws Exception {
        // Arrange
        when(userDao.findByUsername("nonexistent")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/dashboard/financialSummary"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getFinancialSummary_ShouldReturnUnauthorized_WhenUserIsNotAuthenticated() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/dashboard/financialSummary"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getFinancialSummary_ShouldHandleNullValues() throws Exception {
        FinancialSummaryDto financialSummaryDto = new FinancialSummaryDto();
        financialSummaryDto.setTotalBalance(BigDecimal.valueOf(10000.0));
        financialSummaryDto.setTotalIncomes(BigDecimal.valueOf(5000.0));
        financialSummaryDto.setTotalExpenses(BigDecimal.valueOf(3000.0));

        when(userDao.findByUsername("testuser")).thenReturn(testUser);
        when(dashBoardService.getFinancialSummaryDto(1L)).thenReturn(financialSummaryDto);

        mockMvc.perform(get("/api/dashboard/financialSummary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBalance").value(10000.0))
                .andExpect(jsonPath("$.totalIncomes").value(5000.0))
                .andExpect(jsonPath("$.totalExpenses").value(3000.0))
                .andExpect(jsonPath("$.balanceChange").doesNotExist()) // поле null - не должно быть в JSON
                .andExpect(jsonPath("$.incomesChange").doesNotExist());
    }
}