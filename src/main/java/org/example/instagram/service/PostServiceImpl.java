package org.example.instagram.service;

import org.example.instagram.dto.response.PostResponse;
import org.example.instagram.dto.request.PostCreateRequest;
import org.example.instagram.entity.Post;
import org.example.instagram.entity.User;
import org.example.instagram.repository.CommentRepository;
import org.example.instagram.repository.LikeRepository;
import org.example.instagram.repository.PostRepository;
import org.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.example.instagram.entity.User;
import org.example.instagram.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

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

    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userService.findByUsername(username);

        return postRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public long countByUserId(Long userId) {
        return postRepository.countByUserId(userId);
    }


    @Override
    public List<PostResponse> getAllPostsWithStats() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(post -> {
                long likeCount = likeRepository.countByPostId(post.getId());
                long commentCount = commentRepository.countByPostId(post.getId());
                return PostResponse.from(post, commentCount, likeCount);
            })
            .collect(Collectors.toList());
    }
}