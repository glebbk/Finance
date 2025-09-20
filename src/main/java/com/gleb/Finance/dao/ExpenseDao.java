package com.gleb.Finance.dao;

import com.gleb.Finance.models.Expense;

import java.util.List;

public interface ExpenseDao {
    List<Expense> getAllExpense(long id);
}
