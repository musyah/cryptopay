package com.Cryptopay.Controller;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.Service.*;
import com.Cryptopay.dtos.RegistrationRequest;
import com.Cryptopay.dtos.RegistrationResponse;
import com.Cryptopay.dtos.VerificationResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/CryptoApp/Onboard")
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
    public ResponseEntity<?> Add(@RequestBody RegistrationRequest Request) {
//        addUser.register(Request);
        String message = addUser.register(Request);
        RegistrationResponse response = new RegistrationResponse(message);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }
    @GetMapping("/confirm")
    public ResponseEntity<?> register(@RequestParam("code") String code){

        String Confirmation = addUser.confirmCode(code);
        VerificationResponse response = new VerificationResponse(Confirmation);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}