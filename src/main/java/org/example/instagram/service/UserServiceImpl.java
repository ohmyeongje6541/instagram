package org.example.instagram.service;

import lombok.RequiredArgsConstructor;
import org.example.instagram.dto.request.ProfileUpdateRequest;
import org.example.instagram.dto.response.ProfileResponse;
import org.example.instagram.dto.request.SignUpRequest;
import org.example.instagram.dto.response.UserResponse;
import org.example.instagram.entity.Role;
import org.example.instagram.entity.User;
import org.example.instagram.repository.FollowRepository;
import org.example.instagram.repository.PostRepository;
import org.example.instagram.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //    private final FollowService followService;
    private final FollowRepository followRepository;
    //    private final PostService postService;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public User register(SignUpRequest signUpRequest){
        User user = User.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .name(signUpRequest.getName())
            .role(Role.USER)
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .build();
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow();
    }

    @Override
    public ProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow();

        long postCount = postRepository.countByUserId(user.getId());
        long followerCount = followRepository.countByFollowingId(user.getId());
        long followingCount = followRepository.countByFollowerId(user.getId());

        return ProfileResponse.from(user, postCount, followerCount, followingCount);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = findById(userId);
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest) {
        User user = findById(userId);
        user.updateProfile(profileUpdateRequest.getName(), profileUpdateRequest.getBio());
    }
}