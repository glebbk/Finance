package com.gleb.Finance.dto;

import java.math.BigDecimal;

public class WalletBalanceByCategoryDto {
    private String type;
    private String label;
    private BigDecimal amount;

    public WalletBalanceByCategoryDto() {
    }

    public WalletBalanceByCategoryDto(String type, String label, BigDecimal amount) {
        this.type = type;
        this.label = label;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
