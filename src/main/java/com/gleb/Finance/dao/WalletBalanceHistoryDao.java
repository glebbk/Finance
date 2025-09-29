package com.gleb.Finance.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface WalletBalanceHistoryDao {
    BigDecimal getTotalAvailableBalanceByIdAndDate(long userId, LocalDate date);
}
