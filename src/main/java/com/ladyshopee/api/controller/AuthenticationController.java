package com.ladyshopee.api.controller;

import com.ladyshopee.api.error.LoginException;
import com.ladyshopee.api.error.RegistrationException;
import com.ladyshopee.api.model.User;
import com.ladyshopee.api.payload.request.LoginRequest;
import com.ladyshopee.api.payload.request.RegisterRequest;
import com.ladyshopee.api.payload.response.JwtAuthenticationResponse;
import com.ladyshopee.api.payload.response.MessageResponse;
import com.ladyshopee.api.payload.response.Response;
import com.ladyshopee.api.service.AuthenticationService;
import com.ladyshopee.api.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest){
        try{
            Response response = authenticationService.register(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (RegistrationException exception){
            return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value() ,exception.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        try{
            User user = authenticationService.login(loginRequest);
            final String jwt = jwtService.generateToken(user);

            Set<String> roles = new HashSet<>(user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());

            JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .roles(roles)
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        }
        catch (LoginException exception){
            return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value() ,exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}