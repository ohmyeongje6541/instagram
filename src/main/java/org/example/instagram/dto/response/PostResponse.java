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
    private String imageUrl;

    private Long userId;
    private String username;

    private long commentCount;
    private long likeCount;

    // Entity => DTO 변환
    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .imageUrl(post.getImageUrl())
            .userId(post.getUser().getId())
            .username(post.getUser().getUsername())
            .likeCount(0)
            .commentCount(0)
            .build();
    }

    // Entity => DTO 변환
    public static PostResponse from(Post post, long commentCount, long likeCount) {
        return PostResponse.builder()
            .id(post.getId())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .imageUrl(post.getImageUrl())
            .userId(post.getUser().getId())
            .username(post.getUser().getUsername())
            .likeCount(likeCount)
            .commentCount(commentCount)
            .build();
    }
}