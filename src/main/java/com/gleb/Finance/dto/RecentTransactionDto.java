package com.gleb.Finance.dto;

import com.gleb.Finance.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecentTransactionDto {

    private String name;

    private BigDecimal amount;

    private TransactionType type;

    private LocalDate dateTime;

    public RecentTransactionDto() {}

    public RecentTransactionDto(String name, BigDecimal amount, TransactionType type, LocalDate dateTime) {
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
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

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }
}
