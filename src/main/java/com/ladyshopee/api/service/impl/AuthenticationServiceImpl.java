package com.ladyshopee.api.service.impl;

import com.ladyshopee.api.error.LoginException;
import com.ladyshopee.api.error.RegistrationException;
import com.ladyshopee.api.model.ERole;
import com.ladyshopee.api.model.Role;
import com.ladyshopee.api.model.User;
import com.ladyshopee.api.payload.request.LoginRequest;
import com.ladyshopee.api.payload.request.RegisterRequest;
import com.ladyshopee.api.payload.response.MessageResponse;
import com.ladyshopee.api.payload.response.Response;
import com.ladyshopee.api.repository.RoleRepository;
import com.ladyshopee.api.repository.UserRepository;
import com.ladyshopee.api.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private MongoAuthUserDetailServiceImpl userDetailsService;

    @Override
    public Response register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RegistrationException("Error: Username/Email is already in use!");
        }

        Set<Role> roles = extractRoles(registerRequest);

        User user = User
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
        return new MessageResponse(200 ,"User registered successfully!");
    }

    @Override
    public User login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new LoginException("Incorrect username or password", e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return (User) userDetailsService.loadUserByUsername(loginRequest.getEmail());
    }

    private Set<Role> extractRoles(RegisterRequest request) {
        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
                switch(role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }
}
