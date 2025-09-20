package com.gleb.Finance.dto;

import com.gleb.Finance.models.TransactionType;

import java.math.BigDecimal;

public class RecentTransactionDto {

    private String name;

    private BigDecimal amount;

    private TransactionType type;

    public RecentTransactionDto() {}

    public RecentTransactionDto(String name, BigDecimal amount, TransactionType type) {
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
