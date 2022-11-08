package com.Cryptopay.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String Username;
    private String password;
}
