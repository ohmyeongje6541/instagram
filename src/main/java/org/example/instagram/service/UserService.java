package org.example.instagram.service;

import org.example.instagram.dto.request.SignUpRequest;
import org.example.instagram.entity.User;

public interface UserService {

    User register(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);
}
