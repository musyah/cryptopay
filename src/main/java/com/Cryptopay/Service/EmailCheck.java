package com.Cryptopay.Service;

import com.Cryptopay.Repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailCheck {
    @Autowired
    private final UserRepository repository;

    public String emailCheck(String email) {
        String EmailChecker = String.valueOf(repository.findByEmail(email));
        if (EmailChecker.isEmpty()) {
            throw new IllegalStateException("Proceed to Register");
        }
        else {
            return "Wallet with the email already exists";
        }
    }
}
