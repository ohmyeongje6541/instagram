package org.example.instagram.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.instagram.entity.Like;
import org.example.instagram.entity.Post;
import org.example.instagram.entity.User;
import org.example.instagram.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);

        // 좋아요가 있으면
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            // 좋아요가 없으면
        } else {
            Post post = postService.findById(postId);
            User user = userService.findById(userId);

            Like like = Like.builder()
                .post(post)
                .user(user)
                .build();
            likeRepository.save(like);
        }


    }

//    @Override
//    public boolean isLiked() {
//
//    }
//
//    @Override
//    public long getLikeCount() {
//
//    }

}
