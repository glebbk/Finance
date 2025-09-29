package com.gleb.Finance.dto;


import java.math.BigDecimal;
import java.util.List;

public class FinancialDashboardDto {
    private String userName;

    private BigDecimal totalBalance; // сумма "на руках"

    private BigDecimal saving;

    private BigDecimal totalExpenses;

    private BigDecimal totalIncomes;

    private List<ExpenseDto> expenseDtoList;

    private List<IncomeDto> incomeDtoList;

    private List<WalletBalanceDto> walletBalanceDtoList;

    private List<RecentTransactionDto> recentTransactionDtoList;

    public FinancialDashboardDto() {}

    public FinancialDashboardDto(String userName, BigDecimal totalBalance, BigDecimal saving,
                                 BigDecimal totalExpenses, BigDecimal totalIncomes,
                                 List<ExpenseDto> expenseDtoList, List<IncomeDto> incomeDtoList,
                                 List<WalletBalanceDto> walletBalanceDtoList,
                                 List<RecentTransactionDto> recentTransactionDtoList) {
        this.userName = userName;
        this.totalBalance = totalBalance;
        this.saving = saving;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.expenseDtoList = expenseDtoList;
        this.incomeDtoList = incomeDtoList;
        this.walletBalanceDtoList = walletBalanceDtoList;
        this.recentTransactionDtoList = recentTransactionDtoList;
    }

    public List<ExpenseDto> getExpenseDtoList() {
        return expenseDtoList;
    }

    public void setExpenseDtoList(List<ExpenseDto> expenseDtoList) {
        this.expenseDtoList = expenseDtoList;
    }

    public List<IncomeDto> getIncomeDtoList() {
        return incomeDtoList;
    }

    public void setIncomeDtoList(List<IncomeDto> incomeDtoList) {
        this.incomeDtoList = incomeDtoList;
    }

    public List<WalletBalanceDto> getWalletBalanceDtoList() {
        return walletBalanceDtoList;
    }

    public void setWalletBalanceDtoList(List<WalletBalanceDto> walletBalanceDtoList) {
        this.walletBalanceDtoList = walletBalanceDtoList;
    }

    public List<RecentTransactionDto> getRecentTransactionDtoList() {
        return recentTransactionDtoList;
    }

    public void setRecentTransactionDtoList(List<RecentTransactionDto> recentTransactionDtoList) {
        this.recentTransactionDtoList = recentTransactionDtoList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getSaving() {
        return saving;
    }

    public void setSaving(BigDecimal saving) {
        this.saving = saving;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(BigDecimal totalIncomes) {
        this.totalIncomes = totalIncomes;
    }
}
