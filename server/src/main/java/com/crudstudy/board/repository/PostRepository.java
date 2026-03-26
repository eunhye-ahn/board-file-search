package com.crudstudy.board.repository;

import com.crudstudy.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Spring Data JPA - Pageable
     * [WHAT] 대용량 데이터를 잘라서 가져오는 도구
     *
     */
    Page<Post> findAll(Pageable pageable);
}
