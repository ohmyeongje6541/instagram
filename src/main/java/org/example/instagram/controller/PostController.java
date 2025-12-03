package org.example.instagram.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.reponse.CommentResponse;
import org.example.instagram.dto.reponse.PostResponse;
import org.example.instagram.dto.request.CommentRequest;
import org.example.instagram.dto.request.PostCreateRequest;
import org.example.instagram.security.CustomUserDetails;
import org.example.instagram.service.CommentService;
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
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;


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

        List<CommentResponse> comments = commentService.getComments(id);

        model.addAttribute("post", post);
        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("comments", comments);
        return "post/detail";
    }


    @PostMapping("/{postId}/comments")
    public String createComment(
        @PathVariable Long postId,
        @Valid @ModelAttribute CommentRequest commentRequest,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            PostResponse post = postService.getPost(postId);
            List<CommentResponse> comments = commentService.getComments(postId);

            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
//            model.addAttribute("commentRequest", commentCreateRequest);
            return "post/detail";
        }

        commentService.create(postId, commentRequest, userDetails.getId());


        return "redirect:/posts/" + postId;
    }

    @PostMapping("{id}/like")
    public String toggleLike(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        likeService.toggleLike(id, userDetails.getId());
        return "redirect:/posts/" + id;
    }

}
