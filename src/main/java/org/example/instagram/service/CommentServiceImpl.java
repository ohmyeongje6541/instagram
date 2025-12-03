package org.example.instagram.service;

import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.reponse.CommentResponse;
import org.example.instagram.dto.request.CommentCreateRequest;
import org.example.instagram.entity.Comment;
import org.example.instagram.entity.Post;
import org.example.instagram.entity.User;
import org.example.instagram.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse create(
        Long postId,
        CommentCreateRequest commentCreateRequest,
        Long userId) {
        Post post = postService.findById(postId);
        User user = userService.findById(userId);

        Comment comment = Comment.builder()
            .content(commentCreateRequest.getContent())
            .post(post)
            .user(user)
            .build();

        Comment saved = commentRepository.save(comment);
        return CommentResponse.from(saved);
    }
}