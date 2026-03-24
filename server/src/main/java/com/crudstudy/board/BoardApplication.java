package com.crudstudy.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * [WHAT] JPA Auditing 기능 켜달라고 알림
 *
 * [WHY] @CreatedDate, @LastModifiedDate 사용하기 위해
 * 		main에 한번 붙이면 전체프로젝트에 적용됨
 *
 * [흐름] INSERT 발생 → AuditingEntityListener 감지 → createdAt, updatedAt 자동 세팅
 * 		UPDATE 발생 → AuditingEntityListener 감지 → updatedAt 자동 갱신
 */
@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
