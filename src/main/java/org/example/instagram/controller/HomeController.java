package org.example.instagram.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.response.PostResponse;
import org.example.instagram.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {
       // List<PostResponse> posts = postService.getAllPosts();
        List<PostResponse> posts = postService.getAllPostsWithStats();
        model.addAttribute("posts", posts);
        return "home";
    }
}
