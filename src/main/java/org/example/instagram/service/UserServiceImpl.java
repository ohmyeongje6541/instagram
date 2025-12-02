package org.example.instagram.service;

import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.request.SignUpRequest;
import org.example.instagram.entity.Role;
import org.example.instagram.entity.User;
import org.example.instagram.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(SignUpRequest signUpRequest){
        User user = User.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .name(signUpRequest.getName())
            .role(Role.USER)
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .build();
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow();
    }
}
