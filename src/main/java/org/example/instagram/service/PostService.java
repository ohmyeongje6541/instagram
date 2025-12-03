package org.example.instagram.service;

import java.util.List;
import org.example.instagram.dto.response.PostResponse;
import org.example.instagram.dto.request.PostCreateRequest;
import org.example.instagram.entity.Post;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, Long userId); //데이터 저장
    Post findById(Long postId);
    PostResponse getPost(Long postId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByUsername(String username);
}
