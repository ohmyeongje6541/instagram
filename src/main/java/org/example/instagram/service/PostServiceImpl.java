package org.example.instagram.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.reponse.PostResponse;
import org.example.instagram.dto.request.PostCreateRequest;
import org.example.instagram.entity.Post;
import org.example.instagram.entity.User;
import org.example.instagram.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest postCreateRequest, Long userId) {
        User user = userService.findById(userId);

        Post post = Post.builder()
            .content(postCreateRequest.getContent())
            .user(user)
            .build();

        Post saved = postRepository.save(post);
        return PostResponse.from(saved);

    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow();
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = findById(postId);
        return PostResponse.from(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

}