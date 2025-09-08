package com.gleb.Finance.dto;


import java.util.List;

public class FinancialDashboardDto {

    private List<ExpenseDto> expenseDtoList;

    private List<IncomeDto> incomeDtoList;

    private List<WalletBalanceDto> walletBalanceDtoList;

    private List<RecentTransactionDto> recentTransactionDtoList;

    public FinancialDashboardDto() {}

    public FinancialDashboardDto(List<ExpenseDto> expenseDtoList, List<IncomeDto> incomeDtoList,
                                 List<WalletBalanceDto> walletBalanceDtoList,
                                 List<RecentTransactionDto> recentTransactionDtoList) {
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
}
