package org.example.instagram.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.instagram.entity.Follow;
import org.example.instagram.entity.User;
import org.example.instagram.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void toggleFollow(Long followerId, String followingUsername) {
        User follower = userService.findById(followerId);
        User following = userService.findByUsername(followingUsername);

        // 자기 자신 팔로우 방지
        if (follower.getId().equals(following.getId())){
            throw new RuntimeException("자기 자신은 팔로우 할 수 없습니다");
        }

        Optional<Follow> existingFollow = followRepository
            .findByFollowerIdAndFollowingId(follower.getId(), following.getId());
        // follow toggle
        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
        } else {
            Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
            followRepository.save(follow);
        }
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(
            followerId, followingId
        );
    }

    @Override
    public long countByFollowerId(Long followerId) {
        return followRepository.countByFollowerId(followerId);
    }

    @Override
    public long countByFollowingId(Long followingId) {
        return followRepository.countByFollowingId(followingId);
    }

}