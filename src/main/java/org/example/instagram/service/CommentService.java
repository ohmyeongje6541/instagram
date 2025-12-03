package org.example.instagram.service;

import java.util.List;
import org.example.instagram.dto.reponse.CommentResponse;
import org.example.instagram.dto.request.CommentCreateRequest;

public interface CommentService {
    CommentResponse create(Long postId, CommentCreateRequest commentCreateRequest, Long userId);
    List<CommentResponse> getComments(Long postId);
}
