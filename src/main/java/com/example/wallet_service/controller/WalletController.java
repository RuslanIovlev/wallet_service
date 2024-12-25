package com.example.wallet_service.controller;

import com.example.wallet_service.dto.OperationDto;
import com.example.wallet_service.service.WalletService;
import jakarta.validation.Valid;
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
    public String changeBalance(@RequestBody @Valid OperationDto operationDto) {
        return walletService.changeBalance(operationDto);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public String getBalance(@PathVariable(name = "WALLET_UUID") UUID walletId) {
        return walletService.getBalance(walletId);
    }
}
