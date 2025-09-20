package com.gleb.Finance.mapper;

import com.gleb.Finance.dto.ExpenseDto;
import com.gleb.Finance.models.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public static ExpenseDto toDto(Expense expense) {
        if (expense == null) {
            return new ExpenseDto();
        }

        ExpenseDto dto = new ExpenseDto();
        dto.setName(expense.getCategory());
        dto.setAmount(expense.getAmount());

        return dto;
    }

    public static List<ExpenseDto> toDtoList(List<Expense> expenses) {
        if (expenses == null) {
            return new ArrayList<>();
        }

        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }
}
