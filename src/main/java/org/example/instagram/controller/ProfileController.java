package org.example.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.response.UserResponse;
import org.example.instagram.security.CustomUserDetails;
import org.example.instagram.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/edit")
    public String editForm(
        Model model,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserResponse currentUser = userService.getUserById(userDetails.getId());

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setBio(currentUser.getBio());
        profileUpdateRequest.setName(currentUser.getName());


        model.addAttribute("profileUpdateRequest", profileUpdateRequest);
        model.addAttribute("currentUser", currentUser);
        return "profile/edit";
    }
}
