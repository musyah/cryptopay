package com.Cryptopay.Service;

import com.Cryptopay.Entity.ConfirmationToken;
import com.Cryptopay.Entity.RegistrationRequest;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Entity.UserRole;
import com.Cryptopay.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor

public class AddUserService {

    private final UserService userService;
    private final UserRepository repository;
    @Autowired
    private final ConfirmationTokenService tokenService;



    public String register(RegistrationRequest request) {
        return userService.signUpUser(
                new UserInfo(
                        request.getEmail(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPassword(),
                        request.getMobile(),
                        UserRole.USER
                )

        );

    }
    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = tokenService.getToken(token).orElseThrow(()->
                new IllegalStateException("Code not found"));

        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        tokenService.setConfirmedAt(token);
        userService.enableUserInfo(
                confirmationToken.getUserInfo().getEmail());
        return "confirmed";

    }


}
