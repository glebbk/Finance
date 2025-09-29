package com.gleb.Finance.dao;

import com.gleb.Finance.models.Wallet;
import com.gleb.Finance.models.WalletType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WalletDao {
    List<Wallet> getAllWallets(long id);
    BigDecimal getTotalAvailableBalance(long id);
    BigDecimal getTotalNetWorth(long id);
    Map<WalletType, BigDecimal> getBalancesByType(long id);
}
