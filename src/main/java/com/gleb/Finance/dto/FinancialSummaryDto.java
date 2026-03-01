package com.gleb.Finance.dto;

import java.math.BigDecimal;

public class FinancialSummaryDto {
    private BigDecimal totalBalance; // общий баланс (текущий баланс)

    private BigDecimal totalIncomes; // общие доходы

    private BigDecimal totalExpenses; // общие расходы

    private BigDecimal saving; // сбережения (всего средств)
    private BigDecimal savingTarget; // цель

    public FinancialSummaryDto() {}

    public FinancialSummaryDto(BigDecimal totalBalance,
                               BigDecimal totalIncomes,
                               BigDecimal totalExpenses,
                               BigDecimal saving,
                               BigDecimal savingTarget) {
        this.totalBalance = totalBalance;
        this.totalIncomes = totalIncomes;
        this.totalExpenses = totalExpenses;
        this.saving = saving;
        this.savingTarget = savingTarget;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(BigDecimal totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getSaving() {
        return saving;
    }

    public void setSaving(BigDecimal saving) {
        this.saving = saving;
    }

    public BigDecimal getSavingTarget() {
        return savingTarget;
    }

    public void setSavingTarget(BigDecimal savingTarget) {
        this.savingTarget = savingTarget;
    }
}