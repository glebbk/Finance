package com.gleb.Finance.mapper;

import com.gleb.Finance.dto.IncomeDto;
import com.gleb.Finance.models.Income;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeMapper {

    public static IncomeDto toDto(Income income) {
        if (income == null) {
            return new IncomeDto();
        }
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(income.getAmount());
        incomeDto.setName(income.getCategory());
        return incomeDto;
    }

    public static List<IncomeDto> toDtoList(List<Income> incomeList) {
        if (incomeList == null) {
            return new ArrayList<>();
        }
        return incomeList.stream()
                .map(IncomeMapper::toDto)
                .collect(Collectors.toList());
    }
}
