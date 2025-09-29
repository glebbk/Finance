package com.gleb.Finance.dao;

import com.gleb.Finance.dto.RecentTransactionDto;

import java.util.List;

public interface TransactionDao {
    List<RecentTransactionDto> getRecentTransactions (long id, int limit);
}
