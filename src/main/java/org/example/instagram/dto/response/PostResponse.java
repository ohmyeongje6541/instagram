package org.example.instagram.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.example.instagram.entity.Post;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    // Entity => DTO 변환
    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .userId(post.getUser().getId())
            .username(post.getUser().getUsername())
            .build();
    }
}
