package com.crudstudy.board.repository;

import com.crudstudy.board.domain.Comment;
import com.crudstudy.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    @Query("select c from Comment c join fetch c.user where c.post = :post and c.isDeleted = false")
    Page<Comment> findByPostAndIsDeletedFalse(@Param("post") Post post, Pageable pageable);

    /**
     * [n+1문제 해결방법] - fetchjoin 추가
     * SELECT c.*, u.*
     * FROM comment c
     * JOIN user u ON c.user_id = u.id
     * WHERE c.post_id = 1
     * AND c.is_deleted = false
     *
     * 로 기존의 findByPostAndIsDeletedFalse(..) jpa 자동생성 쿼리 덮어쓰기
     *
     */
}
