package com.gleb.Finance.mapper;

import com.gleb.Finance.dto.ExpenseDto;
import com.gleb.Finance.models.Expense;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public static ExpenseDto getExpenseDto(Expense expense) {
        if (expense == null) {
            return new ExpenseDto();
        }
        ExpenseDto dto = new ExpenseDto(expense.getCategory(), expense.getAmount());

        return dto;
    }

    public static List<ExpenseDto> getExpenseDtoList(List<Expense> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(ExpenseMapper::getExpenseDto)
                .collect(Collectors.toList());
    }
}
