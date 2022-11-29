package com.Cryptopay.dtos;

import lombok.*;



@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private final String subject;
    private final Double Amount;
}
