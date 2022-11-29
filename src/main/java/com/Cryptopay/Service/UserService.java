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
    @Autowired
    private final EmailSender emailSender;

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

//        emailCheck.emailCheck(userInfo.getEmail());
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

        String Recipient = (repository.findByEmail(email).getMobile());

            // send otp to phone
            PhoneNumber to = new PhoneNumber(Recipient);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = "Welcome to KryptoPesa your verification code is "+code;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();

            codeService.saveCode(confirmationCode);
            return code;
//        }
//        else {
//            throw  new IllegalStateException("Account enabled proceed to Login");
//        }
    }
    public void resetEmail(String email){
        String link = "http://localhost:8080/api/v1/registration/confirm?code=";
        emailSender.send(
                email,
                buildEmail(repository.findByEmail(email).getFirstName(), link));
    }
    public int enableUserInfo(String email) {
        return repository.enableuserInfo(email);
    }
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}