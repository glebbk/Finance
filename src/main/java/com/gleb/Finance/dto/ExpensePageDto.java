package com.gleb.Finance.dto;

import java.util.List;

public class ExpensePageDto {
    List<ExpenseDto> list;

    public ExpensePageDto(List<ExpenseDto> list) {
        this.list = list;
    }

    public List<ExpenseDto> getList() {
        return list;
    }

    public void setList(List<ExpenseDto> list) {
        this.list = list;
    }
}
