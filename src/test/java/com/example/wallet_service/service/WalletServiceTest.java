package com.example.wallet_service.service;

import com.example.wallet_service.dto.OperationDto;
import com.example.wallet_service.enums.OperationType;
import com.example.wallet_service.exception.NotEnoughFundsException;
import com.example.wallet_service.exception.WalletNotFoundException;
import com.example.wallet_service.model.Wallet;
import com.example.wallet_service.repository.WalletRepository;
import com.example.wallet_service.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    private Wallet wallet;
    private OperationDto deposit;
    private OperationDto withdraw;
    private final Long amount = 100L;
    private final UUID walletId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @BeforeEach
    void setUp() {
        deposit = OperationDto.builder()
                .wallet_id(walletId)
                .amount(amount)
                .operationType(OperationType.DEPOSIT)
                .build();
        withdraw = OperationDto.builder()
                .wallet_id(walletId)
                .amount(amount)
                .operationType(OperationType.WITHDRAW)
                .build();
    }

    @Test
    public void when_createWalletOperationWithTypeDeposit_then_returnSuccess() {
        wallet = Wallet.builder()
                .walletId(walletId)
                .balance(BigDecimal.ZERO)
                .build();
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        String result = walletService.createWalletOperation(deposit);

        assertEquals("The operation is done. Current balance: 100", result);
        assertEquals(BigDecimal.valueOf(100), wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    public void when_createWalletOperationWithTypeWithdraw_then_returnSuccess() {
        wallet = Wallet.builder()
                .walletId(walletId)
                .balance(BigDecimal.valueOf(100L))
                .build();
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        String result = walletService.createWalletOperation(withdraw);

        assertEquals("The operation is done. Current balance: 0", result);
        assertEquals(BigDecimal.valueOf(0), wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    public void when_createWalletOperationWithTypeWithdraw_then_returnEnoughFunds() {
        wallet = Wallet.builder()
                .walletId(walletId)
                .balance(BigDecimal.ZERO)
                .build();
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(NotEnoughFundsException.class, () ->
                walletService.createWalletOperation(withdraw));

        assertEquals("Not enough funds on the wallet", exception.getMessage());
    }

    @Test
    public void when_createWalletOperation_then_returnWalletNotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(WalletNotFoundException.class, () ->
                walletService.createWalletOperation(deposit));

        assertEquals("Wallet not found", exception.getMessage());
    }

    @Test
    public void when_getBalance_then_returnBalance() {
        wallet = Wallet.builder()
                .walletId(walletId)
                .balance(BigDecimal.ZERO)
                .build();
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        String result = walletService.getBalance(walletId);

        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertEquals("Current balance: 0", result);
    }

    @Test
    public void when_getBalance_then_returnWalletNotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(WalletNotFoundException.class, () ->
                walletService.getBalance(walletId));

        assertEquals("Wallet not found", exception.getMessage());
    }
}
