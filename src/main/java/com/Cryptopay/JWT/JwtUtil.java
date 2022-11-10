package com.Cryptopay.JWT;

import com.Cryptopay.Entity.UserInfo;
import com.Cryptopay.dtos.AuthRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.twilio.rest.chat.v1.service.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtUtil {
    @Autowired
    private final HttpServletResponse response;
//    @Autowired
//    private final Authentication authentication;

    public String createToken(AuthRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String currentUserName = authentication.getName();
//        }

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

       Algorithm algorithm = Algorithm.HMAC256("mike".getBytes());
       String access_token = JWT.create()
                .withSubject(request.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000 ))
                .withIssuer(request.getEmail())
                .withClaim("role", user.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(request.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 20 * 60 * 1000 ))
                .withIssuer(request.getEmail())
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        return access_token;
    }
}
