package com.Cryptopay.Controller;

import com.Cryptopay.Entity.RegistrationRequest;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Service.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/CryptoApp/Onboard")
//@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private final AddUserService addUser;
    @Autowired
    private final EmailCheck emailChecker;

    @GetMapping("/users")
    public List<UserInfo> getUser() {
        return userService.getAllUserDetails();
    }

    @GetMapping("/user")
    public UserInfo getUserInfo(String email) {
        return userService.getUserInfo(email);
    }

    @GetMapping("/Check")
    public String check(String email) {
        return emailChecker.emailCheck(email);
    }
   @PostMapping("/Resend")
    public String getCode(String email){
        return userService.resendCode(email);
    }
    @PostMapping("/Save")
    public String Add(@RequestBody RegistrationRequest request) {
        return addUser.register(request);
    }

    @GetMapping("/confirm")
    public String register(@RequestParam("code") String code){
        return addUser.confirmCode(code);
    }
}