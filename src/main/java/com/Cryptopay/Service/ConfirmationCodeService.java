package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Entity.ConfirmationCode;
import com.Cryptopay.Repository.ConfirmationCodeRepository;
import com.Cryptopay.Repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationCodeService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    private final ConfirmationCodeRepository repository;
    private final TwilioConfig twilioConfig;

    public void saveToken(ConfirmationCode token){
        repository.save(token);
    }

    public Optional<ConfirmationCode> getToken(String token) {
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return repository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

//    public String resend(String mobile) {
//        UserInfo userInfo = new UserInfo();
//        int randomNo=(int)(Math.random()*10000)+1000;
//        String token = String.valueOf(randomNo);
//
//        ConfirmationToken confirmationToken = new ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(10),
//                userInfo
//        );
//
//       // ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
//        PhoneNumber to = new PhoneNumber(userInfo.getMobile());
//        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
//        String message = "Welcome to KryptoPesa your verification code is "+confirmationToken.getToken();
//        MessageCreator creator = Message.creator(to, from, message);
//        creator.create();
//        repository.save(confirmationToken);
//        return "token resend";
//    }
}
