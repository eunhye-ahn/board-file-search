package com.crudstudy.board.domain;

import com.crudstudy.board.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Table: comment
 * Columns:
 * id bigint AI PK
 * post_id bigint
 * content text
 * is_deleted tinyint(1)
 * deleted_at datetime
 * created_at datetime
 * updated_at datetim
 */

/**
 * String -> varchar(255) 기본매핑
 * @Column(length = 500) -> varchar(500) 매핑
 * @Column(columnDefinition = "TEXT") -> text ~65,535bytes
 */

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id",nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
