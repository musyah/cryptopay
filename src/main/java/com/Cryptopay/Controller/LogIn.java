package com.Cryptopay.Controller;

//import com.Cryptopay.Filter.CustomAuthenticationFilter;
import com.Cryptopay.JWT.JwtUtil;
//import com.Cryptopay.Filter.CustomAuthenticationFilter;
import com.Cryptopay.dtos.AuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/CryptoApp/")
public class LogIn {
//    @Autowired
//    private final CustomAuthenticationFilter authenticationFilter;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtil jwtUtil;

    @PostMapping("/Login")
    public String generateToken(@RequestBody AuthRequest authRequest){

        return jwtUtil.createToken(authRequest, authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())));
    }


}
