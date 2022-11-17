package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Entity.ConfirmationCode;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.ConfirmationCodeRepository;
import com.Cryptopay.Repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
    private final EmailCheck emailCheck;
    @Autowired
    private final SmsService sms;
    @Autowired
    private final ConfirmationCodeService codeService;
    @Autowired
    private final TwilioConfig twilioConfig;
    @Autowired
    private final ConfirmationCodeRepository codeRepository;

    public List<UserInfo> getAllUserDetails() {

        return repository.findAll();
    }
    public UserInfo getUserInfo(String email){
        return repository.findByEmail(email);

    }

    private final String USER_NOT_FOUND = "user with the email %s not found";
    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
         UserInfo userInfo = repository.findByEmail(Username);
         if(userInfo== null){
             throw new UsernameNotFoundException(
                     String.format(USER_NOT_FOUND, Username));
         }

         return new User(userInfo.getEmail(), userInfo.getPassword(), userInfo.getAuthorities());
    }


    public String signUpUser(UserInfo userInfo){

        emailCheck.emailCheck(userInfo.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
       repository.save(userInfo);

        return sms.sendSms(userInfo);
    }

    public String resendCode(String email){

//        boolean verify = false;
//        if(verify == (repository.findByEmail(email).getEnabled())){

       // UserInfo userInfo = getUserInfo(email);
            int randomNo=(int)(Math.random()*10000)+1000;
            String code = String.valueOf(randomNo);


        ConfirmationCode confirmationCode = new ConfirmationCode(
                    code,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(10),
                    new UserInfo()
            );
            // send otp to phone
            PhoneNumber to = new PhoneNumber(repository.findByEmail(email).getMobile());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = "Welcome to KryptoPesa your verification code is "+code;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();

            codeService.saveToken(confirmationCode);
            return code;
//        }
//        else {
//            throw  new IllegalStateException("Account enabled proceed to Login");
//        }
    }
    public int enableUserInfo(String email) {
        return repository.enableuserInfo(email);
    }
}