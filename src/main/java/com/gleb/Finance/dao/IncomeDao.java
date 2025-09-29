package com.gleb.Finance.dao;

import com.gleb.Finance.models.Income;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeDao {
    List<Income> getAllIncomes(long id);
    BigDecimal getTotalIncome(long id);
}
