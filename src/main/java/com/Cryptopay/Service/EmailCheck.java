package com.Cryptopay.Service;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailCheck {
    @Autowired
    private final UserRepository repository;

    public String emailCheck(String email)  {

//        UserInfo bob;

        try {
             UserInfo bob = repository.findByEmail(email);
             return bob.getMobile();
        }catch (Exception e){

            throw new IllegalStateException("Imekataa");
        }
//        String EmailChecker = bob.getEmail();
//        if (EmailChecker.isEmpty()) {
//            throw new IllegalStateException("Proceed to Register");
//        }
//        else {
//            System.out.println(EmailChecker);
//            return "Wallet with the email already exists";
//        }
    }
}