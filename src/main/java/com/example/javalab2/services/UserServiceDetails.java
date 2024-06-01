package com.example.javalab2.services;

import com.example.javalab2.entities.User;
import com.example.javalab2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceDetails implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        User user = userRepository.findUserByNickName(nickName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with nickname %s not found", nickName));
        } else {
            return user;
        }
    }
}
