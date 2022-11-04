package com.Cryptopay.Service;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailChecker {
    @Autowired
    private final UserRepository repository;

    public void Checker(){
        UserInfo userInfo = new UserInfo();
        boolean userExists = repository.findByEmail(userInfo.getEmail()).isPresent();
        if (userExists){
            throw new IllegalStateException("Wallet with the email already taken");
    }
    }

    public String emailCheck(String email) {
        Optional<UserInfo> emailChecker = repository.findByEmail(email);
        if (emailChecker.isEmpty()) {
            throw new IllegalStateException("Proceed to Register");
        }
        else{
            return "Wallet with the email already exists";
        }
    }
}
