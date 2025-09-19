package com.gleb.Finance.dao;

import com.gleb.Finance.models.Wallet;

import java.util.List;

public interface WalletDao {
    List<Wallet> getWallets(long id);
}
