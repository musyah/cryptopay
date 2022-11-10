package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Config.TwilioInitializer;
import com.Cryptopay.Entity.ConfirmationToken;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.ConfirmationTokenRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SmsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    @Autowired
    private final TwilioConfig twilioConfig;
    private final ConfirmationTokenService tokenService;
    private final ConfirmationTokenRepository repository;



    public String sendSms(UserInfo userInfo) {

        int randomNo=(int)(Math.random()*10000)+1000;
        String token = String.valueOf(randomNo);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                userInfo
        );
        // send otp to phone

        PhoneNumber to = new PhoneNumber(userInfo.getMobile());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String message = "Welcome to KryptoPesa your verification code is "+token;
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();

        tokenService.saveToken(confirmationToken);
        return token;
        }

    }