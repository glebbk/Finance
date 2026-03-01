package com.gleb.Finance.dao;

import com.gleb.Finance.models.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseDao {
    List<Expense> getAllExpense(long userId);
    Optional<Expense> getExpense(long expenseId);
    BigDecimal getTotalExpenseWithDate(long userId, LocalDate from, LocalDate to);
}
