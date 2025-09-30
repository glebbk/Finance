package com.gleb.Finance.dao;

import com.gleb.Finance.models.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseDao {
    List<Expense> getAllExpense(long userId);
    BigDecimal getTotalExpense(long userId);
    BigDecimal getTotalExpenseWithDate(long userId, LocalDate from, LocalDate to);
}
