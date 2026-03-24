package com.crudstudy.board.domain;

import com.crudstudy.board.domain.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Table(name = "file")
@Getter
@NoArgsConstructor
public class File extends Base {
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
}
