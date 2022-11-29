package com.Cryptopay.Controller;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Service.*;
import com.Cryptopay.dtos.RegistrationRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/CryptoApp/Onboard")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private final AddUserService addUser;
    @Autowired
    private final EmailCheck emailChecker;

    @GetMapping("/users")
    public List<UserInfo> getUsers() {
        return userService.getAllUserDetails();
    }

    @GetMapping("/user")
    public UserInfo getUserInfo(String email) {
        return userService.getUserInfo(email);
    }
    @GetMapping("/Check/{email}")
    public void check(@PathVariable String email) {
        emailChecker.emailCheck(email);
    }
   @PostMapping("/Resend")
    public String getCode(@RequestBody String email){
        return userService.resendCode(email);
    }
    @PostMapping("/Save")
    public String Add(@RequestBody RegistrationRequest Request) {
        return addUser.register(Request);
    }

    @GetMapping("/confirm")
    public String register(@RequestParam("code") String code){
        return addUser.confirmCode(code);
    }
}