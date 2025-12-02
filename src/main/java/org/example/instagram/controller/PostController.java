package org.example.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.request.PostCreateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor // final 로 선언된 필드의 생성자를 자동으로 생성
public class PostController {

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("postCreateRequest", new PostCreateRequest());
        return "post/form";
    }

}
