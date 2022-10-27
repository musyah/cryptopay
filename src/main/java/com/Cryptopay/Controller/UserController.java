package com.Cryptopay.Controller;

import com.Cryptopay.Entity.RegistrationRequest;
import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Service.AddUserService;
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

    @GetMapping("/user")
    public List<UserInfo> getUser() {

        return userService.getAllUserDetails();
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

