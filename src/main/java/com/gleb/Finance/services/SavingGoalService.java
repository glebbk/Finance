package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.SavingGoalDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SavingGoalService {

    private final SavingGoalDaoImpl savingGoalDao;

    private final Logger logger = LoggerFactory.getLogger(SavingGoalService.class);

    public SavingGoalService(SavingGoalDaoImpl savingGoalDao) {
        this.savingGoalDao = savingGoalDao;
    }

    public Optional<BigDecimal> getMainSavingGoalTarget(long userId) {
        logger.info("Getting main saving goal target in service for user: {}", userId);
        return savingGoalDao.getMainSavingGoalTarget(userId);
    }
}
