package com.Cryptopay.Controller;


import com.Cryptopay.JWT.JwtUtil;
import com.Cryptopay.dtos.AuthRequest;
import com.Cryptopay.dtos.AuthResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class Login {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String token = jwtUtil.createToken(authRequest);
        AuthResponse response = new AuthResponse(token);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
}
