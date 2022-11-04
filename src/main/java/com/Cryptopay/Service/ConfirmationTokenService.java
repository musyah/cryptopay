package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Entity.ConfirmationToken;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Repository.ConfirmationTokenRepository;
import com.Cryptopay.Repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final ConfirmationTokenRepository repository;
    private final TwilioConfig twilioConfig;

    public void saveToken(ConfirmationToken token){
        repository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return repository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public String resend(String mobile) {
        UserInfo userInfo = new UserInfo();
        int randomNo=(int)(Math.random()*10000)+1000;
        String token = String.valueOf(randomNo);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                userInfo
        );

       // ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        PhoneNumber to = new PhoneNumber(userInfo.getMobile());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String message = "Welcome to KryptoPesa your verification code is "+confirmationToken.getToken();
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
        repository.save(confirmationToken);
        return "token resend";
    }
}
