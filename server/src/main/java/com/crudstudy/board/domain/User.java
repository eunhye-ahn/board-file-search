package com.crudstudy.board.domain;

import com.crudstudy.board.domain.base.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * id	bigint	NO	PRI		auto_increment
 * name	varchar(50)	NO
 * email	varchar(100)	NO      unique
 * password	varchar(255)	YES
 * role	enum('ROLE_USER','ROLE_ADMIN')	NO		ROLE_USER
 * created_at	datetime	NO		CURRENT_TIMESTAMP	DEFAULT_GENERATED
 */

@Entity
@Table(name="user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //유효성검사 추가 예정+유니크??
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;
}
