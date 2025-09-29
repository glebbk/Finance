package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.*;
import com.gleb.Finance.dto.*;
import com.gleb.Finance.mapper.ExpenseMapper;
import com.gleb.Finance.mapper.IncomeMapper;
import com.gleb.Finance.mapper.WalletMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashBoardService {

    private final ExpenseDaoImpl expenseDao;

    private final IncomeDaoImpl incomeDao;

    private final WalletDaoImpl walletDao;

    private final UserDaoImpl userDao;

    private final TransactionDaoImpl transactionDao;

    private final SavingGoalDaoImpl savingGoalDao;

    private final WalletBalanceHistoryDaoImpl walletBalanceHistoryDao;

    public DashBoardService(ExpenseDaoImpl expenseDao, IncomeDaoImpl incomeDao, WalletDaoImpl walletDao, UserDaoImpl userDao, TransactionDaoImpl transactionDao, SavingGoalDaoImpl savingGoalDao, WalletBalanceHistoryDaoImpl walletBalanceHistoryDao) {
        this.expenseDao = expenseDao;
        this.incomeDao = incomeDao;
        this.walletDao = walletDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
        this.savingGoalDao = savingGoalDao;
        this.walletBalanceHistoryDao = walletBalanceHistoryDao;
    }

    public FinancialSummaryDto getFinancialSummaryDto(long id) {
        BigDecimal currentTotalBalance = walletDao.getTotalAvailableBalance(id);
        BigDecimal balanceChange = calculateBalanceChange(id, currentTotalBalance);
        BigDecimal balanceChangePercent = calculateChangeBalancePercent(id, currentTotalBalance);

        BigDecimal totalIncome = incomeDao.getTotalIncome(id);
        BigDecimal incomeChange = calculateIncomeChange(id);
        BigDecimal incomeChangePercent = calculateIncomeChangePercent(id);

        BigDecimal savingTarget = savingGoalDao.findTargetAmountById(id).orElse(BigDecimal.ZERO);

        return new FinancialSummaryDto();
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

    private BigDecimal calculateBalanceChange(long userId, BigDecimal currentTotalBalance) {
        LocalDate lastMonthDate = LocalDate.now().withDayOfMonth(1);
        BigDecimal lastMonthBalance = walletBalanceHistoryDao.getTotalAvailableBalanceByIdAndDate(userId, lastMonthDate);

        return currentTotalBalance.subtract(lastMonthBalance);
    }

    private BigDecimal calculateChangeBalancePercent(long userId, BigDecimal currentBalance) {
        LocalDate lastMonthDate = LocalDate.now().withDayOfMonth(1);
        BigDecimal lastMonthBalance = walletBalanceHistoryDao.getTotalAvailableBalanceByIdAndDate(userId, lastMonthDate);

        // Проверка на null и нулевые значения
        if (lastMonthBalance == null || currentBalance == null) {
            return BigDecimal.ZERO;
        }

        // Если в прошлом месяце был нулевой баланс, а сейчас есть - считаем как +100%
        if (lastMonthBalance.compareTo(BigDecimal.ZERO) == 0 && currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            return BigDecimal.valueOf(100);
        }

        // Если в прошлом месяце был нулевой баланс, а сейчас тоже ноль - 0%
        if (lastMonthBalance.compareTo(BigDecimal.ZERO) == 0 && currentBalance.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // Если в прошлом месяце был баланс, а сейчас ноль - считаем как -100%
        if (lastMonthBalance.compareTo(BigDecimal.ZERO) > 0 && currentBalance.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(100);
        }

        // Основная формула: ((текущий - прошлый) / прошлый) * 100
        BigDecimal difference = currentBalance.subtract(lastMonthBalance);
        BigDecimal percentChange = difference.divide(lastMonthBalance, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return percentChange;
    }

    private BigDecimal calculateIncomeChange(long userId) {
        BigDecimal currentAmount = incomeDao.getTotalIncomeForCurrentMonth(userId);
        BigDecimal lastMonthAmount = incomeDao.getTotalIncomeForLastMonth(userId);

        return lastMonthAmount.subtract(currentAmount);
    }

    private BigDecimal calculateIncomeChangePercent(long userId) {
        BigDecimal currentMonthIncome = incomeDao.getTotalIncomeForCurrentMonth(userId);
        BigDecimal lastMonthIncome = incomeDao.getTotalIncomeForLastMonth(userId);

        // Проверка на null
        if (lastMonthIncome == null || currentMonthIncome == null) {
            return BigDecimal.ZERO;
        }

        // Если в прошлом месяце был нулевой доход, а сейчас есть - считаем как +100%
        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) > 0) {
            return BigDecimal.valueOf(100);
        }

        // Если в прошлом месяце был нулевой доход, а сейчас тоже ноль - 0%
        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // Если в прошлом месяце был доход, а сейчас ноль - считаем как -100%
        if (lastMonthIncome.compareTo(BigDecimal.ZERO) > 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(-100);
        }

        // Основная формула: ((текущий - прошлый) / прошлый) * 100
        BigDecimal difference = currentMonthIncome.subtract(lastMonthIncome);
        BigDecimal percentChange = difference.divide(lastMonthIncome, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return percentChange;
    }
}
