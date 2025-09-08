package com.gleb.Finance.dto;

import java.math.BigDecimal;

public class IncomeDto {

    private String name;

    private BigDecimal amount;

    public IncomeDto() {}

    public IncomeDto(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
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
}
