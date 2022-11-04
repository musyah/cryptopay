package com.Cryptopay.Controller;

import com.Cryptopay.Entity.RegistrationRequest;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Service.AddUserService;
import com.Cryptopay.Service.ConfirmationTokenService;
//import com.Cryptopay.Service.EmailCheck;
import com.Cryptopay.Service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/CryptoApp/Details")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AddUserService addUser;
    @Autowired
    private final ConfirmationTokenService tokenService;
//    @Autowired
//    private final EmailCheck emailChecker;

    @GetMapping("/user")
    public List<UserInfo> getUser() {
        return userService.getAllUserDetails();
    }
    @GetMapping("/Check")
    public String check(String email) {
        emailChecker.emailCheck(email);
        return "";
    }
   @PostMapping("/Resend")
    public String getToken(@RequestParam("mobile")String mobile){
        return tokenService.resend(mobile);
    }
    @PostMapping("/save")
    public String Add(@RequestBody RegistrationRequest request) {
        return addUser.register(request);
    }
    @GetMapping("/confirm")
    public String register(@RequestParam("code") String token){
        return addUser.confirmToken(token);
    }
}