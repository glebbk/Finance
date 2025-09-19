package com.gleb.Finance.mapper;

import com.gleb.Finance.dto.WalletBalanceDto;
import com.gleb.Finance.models.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WalletMapper {

    public static WalletBalanceDto toDto(Wallet wallet) {
        if (wallet == null) {
            return new WalletBalanceDto();
        }
        WalletBalanceDto walletBalanceDto = new WalletBalanceDto();

        walletBalanceDto.setBalance(wallet.getBalance());
        walletBalanceDto.setName(wallet.getName());

        return walletBalanceDto;
    }

    public static List<WalletBalanceDto> toDtoList(List<Wallet> walletList) {
        if (walletList == null) {
            return new ArrayList<>();
        }
        return walletList.stream()
                .map(WalletMapper::toDto)
                .collect(Collectors.toList());
    }
}
