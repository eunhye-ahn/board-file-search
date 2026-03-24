package com.crudstudy.board.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {
    @Column(nullable = false, name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
