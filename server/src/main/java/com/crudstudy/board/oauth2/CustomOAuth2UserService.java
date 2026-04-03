package com.crudstudy.board.oauth2;

import com.crudstudy.board.domain.User;
import com.crudstudy.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

//구글에서 유저정보가져오는 인터페이스 구현체

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService{
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        System.out.println("oauth진입 : "+email+", "+name);
        userRepository.findByEmail(email)
                .orElseGet(()->userRepository.save(User.builder()
                        .name(name)
                        .email(email)
                        .password(null)
                        .build())
                );

        //구글의 기본키는 sub인데 email을 키로 명시해주는게 좋다 보충 필요
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}
