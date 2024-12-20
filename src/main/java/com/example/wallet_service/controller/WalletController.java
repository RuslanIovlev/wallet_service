package com.example.wallet_service.controller;

import com.example.wallet_service.dto.OperationDto;
import com.example.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Validated
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public String createWalletOperation(@RequestBody OperationDto operationDto) {
        return walletService.createWalletOperation(operationDto);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public String getBalance(@PathVariable(name = "WALLET_UUID") UUID walletId) {
        return walletService.getBalance(walletId);
    }
}
