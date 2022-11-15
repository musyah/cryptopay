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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final EmailCheck emailCheck;
    @Autowired
    private final SmsService sms;
    public List<UserInfo> getAllUserDetails() {

        return repository.findAll();
    }

    private final String USER_NOT_FOUND = "user with the email %s not found";
    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
         UserInfo userInfo = repository.findByEmail(Username);
         if(userInfo== null){
             throw new UsernameNotFoundException(
                     String.format(USER_NOT_FOUND, Username));
         }

         return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getPassword(), userInfo.getAuthorities());
    }


    public String signUpUser(UserInfo userInfo){

        emailCheck.emailCheck(userInfo.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
       repository.save(userInfo);

        return sms.sendSms(userInfo);
    }

    public String resendCode(UserInfo userInfo){

        boolean verify = false;
        if(verify == (repository.findByEmail(userInfo.getEmail()).getEnabled())){
            return sms.sendSms(userInfo);
        }
        else {
            throw  new IllegalStateException("Account enabled proceed to Login");
        }
    }
    public int enableUserInfo(String email) {
        return repository.enableuserInfo(email);
    }
}