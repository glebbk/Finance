package com.gleb.Finance.dto;

import java.util.List;

public class WalletsPageDto {
    List<WalletBalanceDto> walletBalanceDtoList;

    public WalletsPageDto(List<WalletBalanceDto> walletBalanceDtoList) {
        this.walletBalanceDtoList = walletBalanceDtoList;
    }

    public List<WalletBalanceDto> getWalletBalanceDtoList() {
        return walletBalanceDtoList;
    }

    public void setWalletBalanceDtoList(List<WalletBalanceDto> walletBalanceDtoList) {
        this.walletBalanceDtoList = walletBalanceDtoList;
    }
}
