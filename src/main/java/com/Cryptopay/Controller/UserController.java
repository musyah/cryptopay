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
@CrossOrigin
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AddUserService addUser;
    @Autowired
    private final EmailCheck emailChecker;

    @GetMapping("/user")
    public List<UserInfo> getUser() {
        return userService.getAllUserDetails();
    }

    @GetMapping("/Check")
    public String check(String email) {
        return emailChecker.emailCheck(email);
    }
   @PostMapping("/Resend")
    public String getToken(@RequestBody UserInfo userInfo){
        return userService.resendCode(userInfo);
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