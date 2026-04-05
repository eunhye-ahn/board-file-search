package com.crudstudy.board.repository;

import com.crudstudy.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 인터페이스끼리는 다중상속가능 => 그래서 인터페이스만들고 구현체를 따로 만든것
 * 클래스는 다중상속 불가능
 */

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
    /**
     * Spring Data JPA - Pageable
     * [WHAT] 대용량 데이터를 잘라서 가져오는 도구
     *
     */
    Page<Post> findAll(Pageable pageable);
}
