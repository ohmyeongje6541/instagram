package org.example.instagram.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import org.example.instagram.entity.Comment;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .userId(comment.getUser().getId())
            .username(comment.getUser().getUsername())
            .build();
    }
}