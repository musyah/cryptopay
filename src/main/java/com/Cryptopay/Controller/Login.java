package com.Cryptopay.Controller;


import com.Cryptopay.JWT.JwtUtil;
import com.Cryptopay.dtos.AuthRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class Login {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final HttpServletResponse response;


    @PostMapping("/login")
    public String userLogin(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return jwtUtil.createToken(authRequest);

        }
//    public void generateToken(@RequestBody AuthRequest authRequest){
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authRequest.getUsername(),
//                        authRequest.getPassword()));
//        jwtUtil.createToken(authRequest);
//    }
}
