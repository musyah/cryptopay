package com.Cryptopay.Service;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.UserRepository;
import com.Cryptopay.dtos.ResetDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailCheck {
    @Autowired
    private final UserRepository repository;

    public String emailCheck(String email)  {

        String EmailChecker = String.valueOf(repository.findByEmail(email));
        if (EmailChecker == null){
            throw new IllegalStateException("Proceed to Register");
        }
        else {
            System.out.println(EmailChecker);
            return "Wallet with the email already exists";
        }
    }
}