package org.example.instagram.repository;

import java.util.List;
import org.example.instagram.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    Long countByPostId(Long postId);
}
