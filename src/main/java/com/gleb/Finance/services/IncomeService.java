package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.IncomeDaoImpl;
import com.gleb.Finance.models.Income;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeDaoImpl incomeDao;

    private final Logger logger = LoggerFactory.getLogger(IncomeService.class);

    public IncomeService(IncomeDaoImpl incomeDao) {
        this.incomeDao = incomeDao;
    }

    public BigDecimal getTotalIncomeWithDate(long userId, LocalDate from, LocalDate to) {
        logger.info("Getting total income with date in service for user: {}", userId);
        return incomeDao.getTotalIncomeWithDate(userId, from, to);
    }

    public List<Income> getIncomesByDateRange(long userId, LocalDate startDate, LocalDate endDate) {
        logger.info("Getting incomes with date range in service for user: {}", userId);
        return incomeDao.getIncomesByDateRange(userId, startDate, endDate);
    }
}
