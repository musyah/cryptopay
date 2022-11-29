package com.Cryptopay.Service;

import com.Cryptopay.Config.TwilioConfig;
import com.Cryptopay.Config.TwilioInitializer;
import com.Cryptopay.Entity.ConfirmationCode;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Entity.Wallet;
import com.Cryptopay.Repository.WalletRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;


@Service
@AllArgsConstructor
public class SmsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    @Autowired
    private final TwilioConfig twilioConfig;
    @Autowired
    private final ConfirmationCodeService codeService;
    @Autowired
    private final WalletRepository walletRepo;
    public int Generator(){
        Random rand = new Random();
        int maxValues= 9999;
        return rand.nextInt(maxValues);
    };
    public int AddressGenerator(){
        Random rand = new Random();
        int maxValues= 99999;
        return rand.nextInt(maxValues);
    };


    public String sendSms(UserInfo userInfo) {

        String pin = String.valueOf(Generator());
        String value = String.valueOf(AddressGenerator());
        String code = String.valueOf(Generator());

        ConfirmationCode confirmationCode = new ConfirmationCode(
                code,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                userInfo
        );
        Wallet wallet = new Wallet(
                userInfo,
                userInfo.getFirstName()+value,
                pin,
                0.0007
        );
        walletRepo.save(wallet);
        try {
            PhoneNumber to = new PhoneNumber(userInfo.getMobile());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = "Welcome " + userInfo.getFirstName() + " to KryptoPesa your verification code is " + code +
                    "your wallet address is ("+wallet.getAddress()+") with the pin: "+pin;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            codeService.saveCode(confirmationCode);
            return code;
        } catch (Exception e) {
            throw new IllegalStateException("Message App in not working properly");
        }
    }
}