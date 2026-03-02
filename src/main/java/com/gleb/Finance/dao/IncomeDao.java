package com.gleb.Finance.dao;

import com.gleb.Finance.models.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeDao {
    List<Income> getAllIncomes(long id);
    Optional<Income> getIncome(long id);
    BigDecimal getTotalIncomeWithDate(long userId, LocalDate startDay, LocalDate endDate);
    List<Income> getIncomesByDateRange(long userId, LocalDate startDate, LocalDate endDate);
}
