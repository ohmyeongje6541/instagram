package org.example.instagram.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.SignUpRequest;
import org.example.instagram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    // 사용자 데이터를 입력받도록 로그인용 빈 종이 출력
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // 사용자 데이터를 입력받도록 회원가입용 빈 종이 출력
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/signup";
    }

    // 사용자 데이터를 받아서 DB에 저장하는 기능
    @PostMapping("/signup")
    public String signup(
        @Valid @ModelAttribute SignUpRequest signUpRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        // username 중복검증
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "중복된 아이디입니다.");
            return "auth/signup";
        }


        userService.register(signUpRequest);

        return "redirect:/auth/login";
    }

}