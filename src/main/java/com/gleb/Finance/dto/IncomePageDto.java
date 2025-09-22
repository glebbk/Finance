package com.gleb.Finance.dto;

import java.util.List;

public class IncomePageDto {
    List<IncomeDto> list;

    public IncomePageDto(List<IncomeDto> list) {
        this.list = list;
    }

    public List<IncomeDto> getList() {
        return list;
    }

    public void setList(List<IncomeDto> list) {
        this.list = list;
    }
}
