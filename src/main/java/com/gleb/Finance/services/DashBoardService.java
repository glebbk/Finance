package com.gleb.Finance.services;

import com.gleb.Finance.daoImpl.ExpenseDaoImpl;
import com.gleb.Finance.daoImpl.IncomeDaoImpl;
import com.gleb.Finance.daoImpl.WalletDaoImpl;
import com.gleb.Finance.dto.ExpenseDto;
import com.gleb.Finance.dto.FinancialDashboardDto;
import com.gleb.Finance.dto.IncomeDto;
import com.gleb.Finance.dto.WalletBalanceDto;
import com.gleb.Finance.mapper.ExpenseMapper;
import com.gleb.Finance.mapper.IncomeMapper;
import com.gleb.Finance.mapper.WalletMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {

    private final ExpenseDaoImpl expenseDao;

    private final IncomeDaoImpl incomeDao;

    private final WalletDaoImpl walletDao;

    public DashBoardService(ExpenseDaoImpl expenseDao, IncomeDaoImpl incomeDao, WalletDaoImpl walletDao) {
        this.expenseDao = expenseDao;
        this.incomeDao = incomeDao;
        this.walletDao = walletDao;
    }

    public FinancialDashboardDto getFinanceDashboard(long id) {
        List<ExpenseDto> expenseDtoList = ExpenseMapper.toDtoList(expenseDao.getAllExpense(id));

        List<IncomeDto> incomeDtoList = IncomeMapper.toDtoList(incomeDao.getAllIncomes(id));

        List<WalletBalanceDto> walletBalanceDtoList = WalletMapper.toDtoList(walletDao.getWallets(id));

        return new FinancialDashboardDto(
                expenseDtoList,
                incomeDtoList,
                walletBalanceDtoList
        );
    }
}
