package com.crudstudy.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Table: file
 * Columns:
 * id bigint AI PK
 * post_id bigint
 * original_name varchar(255)
 * stored_name varchar(255)
 * file_path varchar(500)
 * file_size bigint
 * created_at datetime
 */

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, name="original_name")
    private String originalName;

    @Column(nullable = false, name="stored_name")
    private String storedName;

    @Column(nullable = false, name="file_path", length = 500)
    private String filePath;

    @Column(nullable = false, name="file_size")
    private Long fileSize;

    @Column(nullable = false, name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
