package org.example.instagram.service;

import org.example.instagram.dto.reponse.CommentResponse;
import org.example.instagram.dto.request.CommentCreateRequest;

public interface CommentService {
    CommentResponse create(Long postId, CommentCreateRequest commentCreateRequest, Long userId);
}
