package com.Cryptopay.Service;


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
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final EmailCheck emailChecker;
    @Autowired
    private final SmsService sms;
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

        emailChecker.Checker();

        String encodedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
        repository.save(userInfo);

        return sms.sendSms(userInfo);
    }



    public int enableUserInfo(String email) {
        return repository.enableuserInfo(email);
    }
}


