package org.example.instagram.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.request.ProfileUpdateRequest;
import org.example.instagram.dto.response.UserResponse;
import org.example.instagram.security.CustomUserDetails;
import org.example.instagram.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/edit")
    public String edit(
        @Valid @ModelAttribute ProfileUpdateRequest profileUpdateRequest,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model,
        @RequestParam(value = "profileImg", required = false) MultipartFile profileImg
    ) {
        if (bindingResult.hasErrors()) {
            UserResponse currentUser = userService.getUserById(userDetails.getId());
            model.addAttribute("currentUser", currentUser);
            return "profile/edit";
        }

        userService.updateProfile(userDetails.getId(), profileUpdateRequest, profileImg);
        return "redirect:/users/" + userDetails.getUsername();

    }
}