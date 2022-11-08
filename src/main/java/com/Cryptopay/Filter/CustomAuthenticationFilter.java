//package com.Cryptopay.Filter;
//
//import com.Cryptopay.Entity.UserInfo;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Service;
//
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@Service
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    @Autowired
//    private final AuthenticationManager authenticationManager;
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        UserInfo userInfo = new UserInfo();
//        String username = request.getParameter(userInfo.getEmail());
//        String password = request.getParameter(userInfo.getPassword());
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        return  authenticationManager.authenticate(authenticationToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        User user = (User) authentication.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256("mike".getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000 ))
//                .withIssuer(request.getRequestURI().toString())
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ 20 * 60 * 1000 ))
//                .withIssuer(request.getRequestURI().toString())
//                .sign(algorithm);
//
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
//    }
//}
