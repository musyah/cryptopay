package com.Cryptopay.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String email;
    private String password;
}
