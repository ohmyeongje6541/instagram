package org.example.instagram.service;

import org.example.instagram.dto.SignUpRequest;
import org.example.instagram.entity.User;

public interface UserService {

    User register(SignUpRequest signUpRequest);
}
