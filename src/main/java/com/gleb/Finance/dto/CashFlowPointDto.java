package com.gleb.Finance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CashFlowPointDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private BigDecimal incomes;

    private BigDecimal expenses;

    public CashFlowPointDto() {
    }

    public CashFlowPointDto(LocalDate date, BigDecimal incomes, BigDecimal expenses) {
        this.date = date;
        this.incomes = incomes;
        this.expenses = expenses;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncomes() {
        return incomes;
    }

    public void setIncomes(BigDecimal incomes) {
        this.incomes = incomes;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }
}
