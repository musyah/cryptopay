package com.Cryptopay.dtos;

import lombok.*;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String Cpassword;
    private final String mobile;

}
