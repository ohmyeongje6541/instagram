package org.example.instagram.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.reponse.PostResponse;
import org.example.instagram.dto.request.CommentCreateRequest;
import org.example.instagram.dto.request.PostCreateRequest;
import org.example.instagram.security.CustomUserDetails;
import org.example.instagram.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor // final 로 선언된 필드의 생성자를 자동으로 생성
public class PostController {
    private final PostService postService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("postCreateRequest", new PostCreateRequest());
        return "post/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute PostCreateRequest postCreateRequest,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (bindingResult.hasErrors()) {
            return "post/form";
        }

        postService.create(postCreateRequest, userDetails.getId());

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(
        @PathVariable Long id,
        Model model
    ) {
        PostResponse post = postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("commentRequest", new CommentCreateRequest());
        return "post/detail";
    }
}
