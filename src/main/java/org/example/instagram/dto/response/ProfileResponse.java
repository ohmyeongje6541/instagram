package org.example.instagram.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.instagram.entity.User;

@Getter
@Builder
public class ProfileResponse {
    private Long id;
    private String username;
    private String bio;
    private String name;
    private String profileImageUrl;

    // 통계
    private long postCount;
    private long followerCount;
    private long followingCount;

    // Entity => DTO
    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .bio(user.getBio())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .postCount(0)
            .followerCount(0)
            .followingCount(0)
            .build();
    }


    public static ProfileResponse from(
        User user,
        long postCount,
        long followerCount,
        long followingCount) {
        return ProfileResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .bio(user.getBio())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .postCount(postCount)
            .followerCount(followerCount)
            .followingCount(followingCount)
            .build();

    }


}