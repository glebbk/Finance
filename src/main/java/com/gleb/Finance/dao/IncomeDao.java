package com.gleb.Finance.dao;

import com.gleb.Finance.models.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeDao {
    List<Income> getAllIncomes(long id);
    BigDecimal getTotalIncome(long id);
    BigDecimal getTotalIncomeWithDate(long userId, LocalDate from, LocalDate to);
}
