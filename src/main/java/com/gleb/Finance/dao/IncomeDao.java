package com.gleb.Finance.dao;

import com.gleb.Finance.models.Income;

import java.util.List;

public interface IncomeDao {
    List<Income> getAllIncomes(long id);
}
