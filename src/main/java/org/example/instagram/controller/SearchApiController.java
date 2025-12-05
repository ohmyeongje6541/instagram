package org.example.instagram.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.response.PostResponse;
import org.example.instagram.dto.response.UserResponse;
import org.example.instagram.service.PostService;
import org.example.instagram.service.UserService;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchApiController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/users")
    public List<UserResponse> searchUsers(
        @RequestParam String q
    ) {
        return userService.searchUsers(q.trim());
    }

    @GetMapping("/posts")
    public Slice<PostResponse> searchPosts() {

    }
}
