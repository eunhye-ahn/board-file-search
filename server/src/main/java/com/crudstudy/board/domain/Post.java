package com.crudstudy.board.domain;

import com.crudstudy.board.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * creationTimestamp vs createdDate (스프링 친화적 but 설정필요) => @EntityListeners
 */

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name="view_count")
    private int viewCount = 0;

    @Column(name="is_deleted")
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void delete(){
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }
    public void update(String title, String content){
        title = this.getTitle();
        content = this.getContent();
    }
}
