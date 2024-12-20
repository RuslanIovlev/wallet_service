package com.example.wallet_service.service.impl;

import com.example.wallet_service.dto.OperationDto;
import com.example.wallet_service.exception.NotEnoughFundsException;
import com.example.wallet_service.exception.WalletNotFoundException;
import com.example.wallet_service.model.Wallet;
import com.example.wallet_service.repository.WalletRepository;
import com.example.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public String createWalletOperation(OperationDto operationDto) {
        log.info("Starting to create wallet operation: {}", operationDto);
        Wallet wallet = findWalletById(operationDto.wallet_id());
        log.info("Found wallet with id: {}", wallet.getWalletId());
        switch (operationDto.operationType()) {
            case DEPOSIT:
                applyDeposit(wallet, BigDecimal.valueOf(operationDto.amount()));
                break;
            case WITHDRAW:
                applyWithdrawal(wallet, BigDecimal.valueOf(operationDto.amount()));
                break;
            default:
                throw new IllegalArgumentException("Unknown operation type");
        }
        walletRepository.save(wallet);
        log.info("Successfully completed wallet operation for wallet id: {}", wallet.getWalletId());
        return String.format("The operation is done. Current balance: %s", wallet.getBalance().toString());
    }

    @Override
    public String getBalance(UUID walletId) {
        Wallet wallet = findWalletById(walletId);
        log.info("Successfully found wallet balance for wallet id: {}", wallet.getWalletId());
        return String.format("Current balance: %s", wallet.getBalance().toString());
    }

    private void applyDeposit(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));
    }

    private void applyWithdrawal(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsException("Not enough funds on the wallet");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
    }

    private Wallet findWalletById(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found");
                    return new WalletNotFoundException("Wallet not found");
                });
    }
}
