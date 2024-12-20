package com.example.wallet_service.service;

import com.example.wallet_service.dto.OperationDto;

import java.util.UUID;

public interface WalletService {

    String createWalletOperation(OperationDto operationDto);

    String getBalance(UUID walletId);
}
