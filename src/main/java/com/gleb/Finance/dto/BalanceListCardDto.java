package com.gleb.Finance.dto;

import java.math.BigDecimal;
import java.util.List;

public class BalanceListCardDto {
    private List<BalanceCardDto> balanceCardDtoList;

    public BalanceListCardDto() {}

    public BalanceListCardDto(List<BalanceCardDto> balanceCardDtoList) {
        this.balanceCardDtoList = balanceCardDtoList;
    }

    public List<BalanceCardDto> getBalanceCardDtoList() {
        return balanceCardDtoList;
    }

    public void setBalanceCardDtoList(List<BalanceCardDto> balanceCardDtoList) {
        this.balanceCardDtoList = balanceCardDtoList;
    }

    public static class BalanceCardDto {
        private String title;
        private BigDecimal totalAmount;
        private List<WalletBalanceDto> items;

        public BalanceCardDto() {}

        public BalanceCardDto(String title, BigDecimal totalAmount, List<WalletBalanceDto> items) {
            this.title = title;
            this.totalAmount = totalAmount;
            this.items = items;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<WalletBalanceDto> getItems() {
            return items;
        }

        public void setItems(List<WalletBalanceDto> items) {
            this.items = items;
        }
    }
}