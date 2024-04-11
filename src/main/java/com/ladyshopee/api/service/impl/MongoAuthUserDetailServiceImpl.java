package com.ladyshopee.api.service.impl;

import com.ladyshopee.api.repository.UserRepository;
import com.ladyshopee.api.service.MongoAuthUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MongoAuthUserDetailServiceImpl implements MongoAuthUserDetailService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
