package com.crudstudy.board.service;

import com.crudstudy.board.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    //================공통메서드=======================
    public Long getUserId() {return user.getId();};
    public String getUserName() {return user.getName();};


    //================생성자=======================
    //일반로그인용
    public CustomUserDetails(User user) {
        this.user = user;
    }
    //oauth2 로그인용
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    //================UserDetails (일반 로그인)=======================
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }


    // ========== OAuth2User (OAuth2 로그인) ==========
    @Override
    public String getName() {
        return user.getEmail();
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    //getAttribute("email")로 값 추출 가능
    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }


    //=================================================
    // 계정기간 만료 - 1년 이상 미접속 휴면계정 처리할 때
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정잠금 - 로그인 5회 연속 실패 시 계정 잠금할 때
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호 만료 - 90일마다 비밀번호 변경 강제할 때 (은행, 관공서 같은데)
    @Override
    public boolean isCredentialsNonExpired() {
        if (user.getPassword() == null) return true;
        return true;
    }
    // 계정 활성화 - 회원가입 후 이메일 인증 안 하면 로그인 막을 때
    @Override
    public boolean isEnabled() {
        return true;
    }
}
