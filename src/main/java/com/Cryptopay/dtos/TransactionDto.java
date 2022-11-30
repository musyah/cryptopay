package com.Cryptopay.dtos;

import lombok.*;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private  String subject;
    private  Double Amount;
    private String email;

}
