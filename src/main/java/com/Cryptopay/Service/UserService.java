package com.Cryptopay.Service;


import com.Cryptopay.Entity.ConfirmationToken;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final ConfirmationTokenService tokenService;
    public List<UserInfo> getAllUserDetails() {

        return repository.findAll();
    }


    private final String USER_NOT_FOUND = "user with the email %s not found";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND, email))
        );
    }
    public String signUpUser(UserInfo userInfo){
        boolean userExists = repository.findByEmail(userInfo.getEmail()).isPresent();
        if (userExists){
            throw new IllegalStateException("Wallet with the email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
        repository.save(userInfo);

        int randomNo=(int)(Math.random()*100000)+10000;
        String token = String.valueOf(randomNo);

//        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                userInfo
        );
        //todo: send otp to phone 
        tokenService.saveToken(confirmationToken);
        return token;
    }

    public int enableUserInfo(String email) {
        return repository.enableuserInfo(email);


    }

}


