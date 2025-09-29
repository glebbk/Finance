package com.gleb.Finance.models;

public enum WalletType {
    CASH("Наличные"),
    BANK_ACCOUNT("Банковский счет"),
    CREDIT_CARD("Кредитная карта"),
    INVESTMENT("Инвестиции"),
    DEBT("Долги (вам должны)"),
    LOAN("Кредиты (вы должны)"),
    SAVINGS("Накопления"),
    OTHER("Другое");

    private final String displayName;

    WalletType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
