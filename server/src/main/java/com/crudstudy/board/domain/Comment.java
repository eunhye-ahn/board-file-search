package com.crudstudy.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

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
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
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
    private Date deletedAt;

    @Column(nullable = false, name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
