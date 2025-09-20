package com.gleb.Finance.dto;

import java.math.BigDecimal;

public class WalletBalanceDto {
    private String name;
    private BigDecimal balance;

    public WalletBalanceDto() {}

    public WalletBalanceDto(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
