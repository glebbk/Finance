package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.WalletDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletDaoImpl walletDao;

    private final Logger logger = LoggerFactory.getLogger(WalletService.class);

    public WalletService(WalletDaoImpl walletDao) {
        this.walletDao = walletDao;
    }

    public BigDecimal getTotalAvailableBalance(long userId) {
        logger.info("Getting total available balance in service for user: {}", userId);
        return walletDao.getTotalAvailableBalance(userId);
    }

    public BigDecimal getTotalNetWorth(long userId) {
        logger.info("Getting total net worth in service for user: {}", userId);
        return walletDao.getTotalNetWorth(userId);
    }
}
