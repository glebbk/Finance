package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.ExpenseDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ExpenseService {

    private final ExpenseDaoImpl expenseDao;

    private final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(ExpenseDaoImpl expenseDao) {
        this.expenseDao = expenseDao;
    }

    public BigDecimal getTotalExpenseWithDate(long userId, LocalDate from, LocalDate to) {
        logger.info("Getting total expense with date in service for user: {}", userId);
        return expenseDao.getTotalExpenseWithDate(userId, from, to);
    }
}
