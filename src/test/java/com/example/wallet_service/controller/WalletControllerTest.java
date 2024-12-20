package com.example.wallet_service.controller;

import com.example.wallet_service.dto.OperationDto;
import com.example.wallet_service.enums.OperationType;
import com.example.wallet_service.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletService walletService;

    private OperationDto operationDto;

    private final UUID walletId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
        operationDto = OperationDto.builder()
                .wallet_id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .amount(100L)
                .operationType(OperationType.DEPOSIT)
                .build();
    }

    @Test
    public void when_createWalletOperation_thenReturnSuccess() throws Exception {
        String expectedResponse = "The operation is done. Current balance: 100";

        when(walletService.createWalletOperation(any(OperationDto.class))).thenReturn(expectedResponse);
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(operationDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void when_getBalance_thenReturnSuccess() throws Exception {
        String expectedResponse = "Current balance: 100";

        when(walletService.getBalance(walletId)).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/v1/wallets/{WALLET_UUID}", walletId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
