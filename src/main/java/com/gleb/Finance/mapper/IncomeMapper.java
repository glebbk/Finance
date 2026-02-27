package com.gleb.Finance.mapper;

import com.gleb.Finance.dto.IncomeDto;
import com.gleb.Finance.models.Income;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeMapper {

    public static IncomeDto getIncomeDto(Income income) {
        if (income == null) {
            return new IncomeDto();
        }
        IncomeDto incomeDto = new IncomeDto(income.getCategory(), income.getAmount());

        return incomeDto;
    }

    public static List<IncomeDto> getIncomeDtoList(List<Income> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(IncomeMapper::getIncomeDto)
                .collect(Collectors.toList());
    }

}
