package com.Cryptopay.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private final String subject;
    private final BigDecimal AmountSent;
    private final BigDecimal AmountReceived;
}
