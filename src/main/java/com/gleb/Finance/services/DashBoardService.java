package com.gleb.Finance.services;

import com.gleb.Finance.dto.FinancialSummaryDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DashBoardService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final WalletService walletService;
    private final SavingGoalService savingGoalService;

    public DashBoardService(IncomeService incomeService, ExpenseService expenseService, WalletService walletService, SavingGoalService savingGoalService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.walletService = walletService;
        this.savingGoalService = savingGoalService;
    }

    public FinancialSummaryDto getFinancialSummaryDto(long userId) {
        //TODO позже поменять (когда накидаю в базу норм данных)
        LocalDate now = LocalDate.now().plusYears(1);
        LocalDate cur = now.minusYears(1);

        BigDecimal totalBalance = walletService.getTotalAvailableBalance(userId);

        BigDecimal totalIncome = incomeService.getTotalIncomeWithDate(userId, cur, now);

        BigDecimal totalExpense = expenseService.getTotalExpenseWithDate(userId, cur, now);

        BigDecimal savings = walletService.getTotalNetWorth(userId);

        Optional<BigDecimal> goal = savingGoalService.getMainSavingGoalTarget(userId);

        return new FinancialSummaryDto(
                totalBalance,
                totalIncome,
                totalExpense,
                savings,
                goal.orElse(null)
        );
    }

//    public FinancialDashboardDto getFinanceDashboard(long id) {
//        String userName = userDao.getUser(id).getUsername();
//
//        List<ExpenseDto> expenseDtoList = ExpenseMapper.toDtoList(expenseDao.getAllExpense(id));
//
//        List<IncomeDto> incomeDtoList = IncomeMapper.toDtoList(incomeDao.getAllIncomes(id));
//
//        List<WalletBalanceDto> walletBalanceDtoList = WalletMapper.toDtoList(walletDao.getAllWallets(id));
//
//        List<RecentTransactionDto> recentTransactionDtoList = transactionDao.getRecentTransactions(id, 10);
//
//        return new FinancialDashboardDto(
//                userName,
//                expenseDtoList,
//                incomeDtoList,
//                walletBalanceDtoList,
//                recentTransactionDtoList
//        );
//    }

//    public WalletsPageDto getWallets(long id) {
//        List<WalletBalanceDto> walletBalanceDtoList = WalletMapper.toDtoList(walletDao.getAllWallets(id));
//        return new WalletsPageDto(walletBalanceDtoList);
//    }
//
//    public ExpensePageDto getExpenses(long id) {
//        List<ExpenseDto> expenseDtoList = ExpenseMapper.toDtoList(expenseDao.getAllExpense(id));
//        return new ExpensePageDto(expenseDtoList);
//    }
//
//    public IncomePageDto getIncomes(long id) {
//        List<IncomeDto> incomeDtoList = IncomeMapper.toDtoList(incomeDao.getAllIncomes(id));
//        return new IncomePageDto(incomeDtoList);
//    }

//    private BigDecimal calculateBalanceChange(long userId, BigDecimal currentTotalBalance) {
//        LocalDate lastMonthDate = LocalDate.now().withDayOfMonth(1);
//        BigDecimal lastMonthBalance = walletBalanceHistoryDao.getTotalAvailableBalanceByIdAndDate(userId, lastMonthDate);
//
//        return currentTotalBalance.subtract(lastMonthBalance);
//    }
//
//    private BigDecimal calculateChangeBalancePercent(long userId, BigDecimal currentBalance) {
//        LocalDate lastMonthDate = LocalDate.now().withDayOfMonth(1);
//        BigDecimal lastMonthBalance = walletBalanceHistoryDao.getTotalAvailableBalanceByIdAndDate(userId, lastMonthDate);
//
//        // Проверка на null и нулевые значения
//        if (lastMonthBalance == null || currentBalance == null) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был нулевой баланс, а сейчас есть - считаем как +100%
//        if (lastMonthBalance.compareTo(BigDecimal.ZERO) == 0 && currentBalance.compareTo(BigDecimal.ZERO) > 0) {
//            return BigDecimal.valueOf(100);
//        }
//
//        // Если в прошлом месяце был нулевой баланс, а сейчас тоже ноль - 0%
//        if (lastMonthBalance.compareTo(BigDecimal.ZERO) == 0 && currentBalance.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был баланс, а сейчас ноль - считаем как -100%
//        if (lastMonthBalance.compareTo(BigDecimal.ZERO) > 0 && currentBalance.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.valueOf(100);
//        }
//
//        // Основная формула: ((текущий - прошлый) / прошлый) * 100
//        BigDecimal difference = currentBalance.subtract(lastMonthBalance);
//        BigDecimal percentChange = difference.divide(lastMonthBalance, 4, BigDecimal.ROUND_HALF_UP)
//                .multiply(BigDecimal.valueOf(100));
//
//        return percentChange;
//    }
//
//    private BigDecimal calculateIncomeChange(long userId, BigDecimal currentAmount) {
//        BigDecimal lastMonthAmount = incomeDao.getTotalIncomeWithDate(userId,
//                LocalDate.now().minusMonths(1).withDayOfMonth(1),
//                LocalDate.now().withDayOfMonth(1).minusDays(1));
//
//        return lastMonthAmount.subtract(currentAmount);
//    }
//
//    private BigDecimal calculateExpenseChange(long userId, BigDecimal currentAmount) {
//        BigDecimal lastMonthAmount = expenseDao.getTotalExpenseWithDate(userId,
//                LocalDate.now().minusMonths(1).withDayOfMonth(1),
//                LocalDate.now().withDayOfMonth(1).minusDays(1));
//
//        return lastMonthAmount.subtract(currentAmount);
//    }
//
//    private BigDecimal calculateIncomeChangePercent(long userId, BigDecimal currentMonthIncome) {
//        BigDecimal lastMonthIncome = incomeDao.getTotalIncomeWithDate(userId,
//                LocalDate.now().minusMonths(1).withDayOfMonth(1),
//                LocalDate.now().withDayOfMonth(1).minusDays(1));
//
//        // Проверка на null
//        if (lastMonthIncome == null || currentMonthIncome == null) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был нулевой доход, а сейчас есть - считаем как +100%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) > 0) {
//            return BigDecimal.valueOf(100);
//        }
//
//        // Если в прошлом месяце был нулевой доход, а сейчас тоже ноль - 0%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был доход, а сейчас ноль - считаем как -100%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) > 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.valueOf(-100);
//        }
//
//        // Основная формула: ((текущий - прошлый) / прошлый) * 100
//        BigDecimal difference = currentMonthIncome.subtract(lastMonthIncome);
//        BigDecimal percentChange = difference.divide(lastMonthIncome, 4, BigDecimal.ROUND_HALF_UP)
//                .multiply(BigDecimal.valueOf(100));
//
//        return percentChange;
//    }
//
//    private BigDecimal calculateExpenseChangePercent(long userId, BigDecimal currentMonthIncome) {
//        BigDecimal lastMonthIncome = expenseDao.getTotalExpenseWithDate(userId,
//                LocalDate.now().minusMonths(1).withDayOfMonth(1),
//                LocalDate.now().withDayOfMonth(1).minusDays(1));
//
//        // Проверка на null
//        if (lastMonthIncome == null || currentMonthIncome == null) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был нулевой доход, а сейчас есть - считаем как +100%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) > 0) {
//            return BigDecimal.valueOf(100);
//        }
//
//        // Если в прошлом месяце был нулевой доход, а сейчас тоже ноль - 0%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) == 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.ZERO;
//        }
//
//        // Если в прошлом месяце был доход, а сейчас ноль - считаем как -100%
//        if (lastMonthIncome.compareTo(BigDecimal.ZERO) > 0 && currentMonthIncome.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.valueOf(-100);
//        }
//
//        // Основная формула: ((текущий - прошлый) / прошлый) * 100
//        BigDecimal difference = currentMonthIncome.subtract(lastMonthIncome);
//        BigDecimal percentChange = difference.divide(lastMonthIncome, 4, BigDecimal.ROUND_HALF_UP)
//                .multiply(BigDecimal.valueOf(100));
//
//        return percentChange;
//    }
}
