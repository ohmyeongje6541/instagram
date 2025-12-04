package org.example.instagram.service;

import org.example.instagram.dto.response.ProfileResponse;
import org.example.instagram.dto.request.SignUpRequest;
import org.example.instagram.dto.response.UserResponse;
import org.example.instagram.entity.User;

public interface UserService {

    User register(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    User findById(Long userId);

    ProfileResponse getProfile(String username);

    User findByUsername(String username);

    UserResponse getUserById(Long userId);
}