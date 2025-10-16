package com.gleb.Finance.controllers;

import com.gleb.Finance.dto.WalletBalanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @GetMapping("/wallets/{userId}")
    public ResponseEntity<WalletBalanceDto> getWallets(
            @PathVariable long userId) {
        return null;
    }
}
