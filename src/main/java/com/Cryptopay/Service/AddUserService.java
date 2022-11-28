package com.Cryptopay.Service;

import com.Cryptopay.Entity.ConfirmationCode;
import com.Cryptopay.dtos.RegistrationRequest;
import com.Cryptopay.Entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor

public class AddUserService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final ConfirmationCodeService codeService;

    public String register(RegistrationRequest request) {
        return userService.signUpUser(
                new UserInfo(
                        request.getEmail(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPassword(),
                        request.getCpassword(),
                        request.getMobile()
                )
        );
    }
    @Transactional
    public String confirmCode(String code){
        ConfirmationCode confirmationCode = codeService.getCode(code).orElseThrow(()->
                new IllegalStateException("Code not found"));

        if(confirmationCode.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        codeService.setConfirmedAt(code);
        userService.enableUserInfo(
                confirmationCode.getUserInfo().getEmail());
        return "confirmed";
    }


}
