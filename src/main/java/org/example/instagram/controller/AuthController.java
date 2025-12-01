package org.example.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.example.instagram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

}
