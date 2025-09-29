package com.gleb.Finance.dto;

import java.math.BigDecimal;

public class FinancialSummaryDto {
    private BigDecimal totalBalance; // общий баланс
    private BigDecimal balanceChange; // изменение баланса относительно прошлого месяца
    private BigDecimal balanceChangePercent; // изменение баланса относительно прошлого месяца в процентах

    private BigDecimal totalIncomes; // общие доходы
    private BigDecimal incomesChange; // измение доходов относительно прошлого месяца
    private BigDecimal incomesChangePercent; // изменение доходов относительно прошлого месяца в процентах

    private BigDecimal totalExpenses; // общие расходы
    private BigDecimal expensesChange; // изменение расходов относительно прошлого месяца
    private BigDecimal expensesChangePercent; // изменение расходов относительно прошлого месяца в процентах

    private BigDecimal saving; // сбережения (всего средств)
    private BigDecimal savingProgress; // текущий прогресс
    private BigDecimal savingTarget; // цель

    public FinancialSummaryDto() {}

    public FinancialSummaryDto(BigDecimal totalBalance, BigDecimal balanceChange,
                               BigDecimal balanceChangePercent, BigDecimal totalIncomes,
                               BigDecimal incomesChange, BigDecimal incomesChangePercent,
                               BigDecimal totalExpenses, BigDecimal expensesChange,
                               BigDecimal expensesChangePercent, BigDecimal saving,
                               BigDecimal savingProgress, BigDecimal savingTarget) {
        this.totalBalance = totalBalance;
        this.balanceChange = balanceChange;
        this.balanceChangePercent = balanceChangePercent;
        this.totalIncomes = totalIncomes;
        this.incomesChange = incomesChange;
        this.incomesChangePercent = incomesChangePercent;
        this.totalExpenses = totalExpenses;
        this.expensesChange = expensesChange;
        this.expensesChangePercent = expensesChangePercent;
        this.saving = saving;
        this.savingProgress = savingProgress;
        this.savingTarget = savingTarget;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }

    public BigDecimal getBalanceChangePercent() {
        return balanceChangePercent;
    }

    public void setBalanceChangePercent(BigDecimal balanceChangePercent) {
        this.balanceChangePercent = balanceChangePercent;
    }

    public BigDecimal getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(BigDecimal totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public BigDecimal getIncomesChange() {
        return incomesChange;
    }

    public void setIncomesChange(BigDecimal incomesChange) {
        this.incomesChange = incomesChange;
    }

    public BigDecimal getIncomesChangePercent() {
        return incomesChangePercent;
    }

    public void setIncomesChangePercent(BigDecimal incomesChangePercent) {
        this.incomesChangePercent = incomesChangePercent;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getExpensesChange() {
        return expensesChange;
    }

    public void setExpensesChange(BigDecimal expensesChange) {
        this.expensesChange = expensesChange;
    }

    public BigDecimal getExpensesChangePercent() {
        return expensesChangePercent;
    }

    public void setExpensesChangePercent(BigDecimal expensesChangePercent) {
        this.expensesChangePercent = expensesChangePercent;
    }

    public BigDecimal getSaving() {
        return saving;
    }

    public void setSaving(BigDecimal saving) {
        this.saving = saving;
    }

    public BigDecimal getSavingProgress() {
        return savingProgress;
    }

    public void setSavingProgress(BigDecimal savingProgress) {
        this.savingProgress = savingProgress;
    }

    public BigDecimal getSavingTarget() {
        return savingTarget;
    }

    public void setSavingTarget(BigDecimal savingTarget) {
        this.savingTarget = savingTarget;
    }
}