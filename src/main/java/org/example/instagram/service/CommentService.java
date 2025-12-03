package org.example.instagram.service;

import java.util.List;
import org.example.instagram.dto.response.CommentResponse;
import org.example.instagram.dto.request.CommentRequest;

public interface CommentService {
    CommentResponse create(Long postId, CommentRequest commentCreateRequest, Long userId);
    List<CommentResponse> getComments(Long postId);
}
