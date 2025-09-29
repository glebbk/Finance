package com.gleb.Finance.dao;

import com.gleb.Finance.models.SavingGoal;

import java.math.BigDecimal;
import java.util.Optional;

public interface SavingGoalDao {
    Optional<SavingGoal> findById(long id);
    Optional<BigDecimal> findTargetAmountById(long id);
    void save(SavingGoal savingGoal);
    void update(SavingGoal savingGoal);
    void delete(long id);
}
