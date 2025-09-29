package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.*;
import com.gleb.Finance.dto.*;
import com.gleb.Finance.mapper.ExpenseMapper;
import com.gleb.Finance.mapper.IncomeMapper;
import com.gleb.Finance.mapper.WalletMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashBoardService {

    private final ExpenseDaoImpl expenseDao;

    private final IncomeDaoImpl incomeDao;

    private final WalletDaoImpl walletDao;

    private final UserDaoImpl userDao;

    private final TransactionDaoImpl transactionDao;

    public DashBoardService(ExpenseDaoImpl expenseDao, IncomeDaoImpl incomeDao, WalletDaoImpl walletDao, UserDaoImpl userDao, TransactionDaoImpl transactionDao) {
        this.expenseDao = expenseDao;
        this.incomeDao = incomeDao;
        this.walletDao = walletDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }

    public FinancialDashboardDto getFinanceDashboard(long id) {
        String userName = userDao.getUser(id).getUsername();

        BigDecimal totalBalance = walletDao.getTotalAvailableBalance(id);

        BigDecimal saving = walletDao.getTotalNetWorth(id);

        BigDecimal totalIncome = incomeDao.getTotalIncome(id);

        BigDecimal totalExpense = expenseDao.getTotalExpense(id);

        List<ExpenseDto> expenseDtoList = ExpenseMapper.toDtoList(expenseDao.getAllExpense(id));

        List<IncomeDto> incomeDtoList = IncomeMapper.toDtoList(incomeDao.getAllIncomes(id));

        List<WalletBalanceDto> walletBalanceDtoList = WalletMapper.toDtoList(walletDao.getAllWallets(id));

        List<RecentTransactionDto> recentTransactionDtoList = transactionDao.getRecentTransactions(id, 10);

        return new FinancialDashboardDto(
                userName,
                totalBalance,
                saving,
                totalIncome,
                totalExpense,
                expenseDtoList,
                incomeDtoList,
                 walletBalanceDtoList,
                recentTransactionDtoList
        );
    }

    public WalletsPageDto getWallets(long id) {
        List<WalletBalanceDto> walletBalanceDtoList = WalletMapper.toDtoList(walletDao.getAllWallets(id));
        return new WalletsPageDto(walletBalanceDtoList);
    }

    public ExpensePageDto getExpenses(long id) {
        List<ExpenseDto> expenseDtoList = ExpenseMapper.toDtoList(expenseDao.getAllExpense(id));
        return new ExpensePageDto(expenseDtoList);
    }

    public IncomePageDto getIncomes(long id) {
        List<IncomeDto> incomeDtoList = IncomeMapper.toDtoList(incomeDao.getAllIncomes(id));
        return new IncomePageDto(incomeDtoList);
    }
}
