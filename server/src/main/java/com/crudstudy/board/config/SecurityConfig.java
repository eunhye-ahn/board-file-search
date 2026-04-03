package com.crudstudy.board.config;

import com.crudstudy.board.oauth2.CustomOAuth2UserService;
import com.crudstudy.board.oauth2.OAuth2SuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * [WHAT] 인증+인가를 해주는 틀
 *          -> 그걸 무엇으로 처리하는가? 세션 or JWT
 * [흐름] HTTP요청 > FilterChain(필터들이 순서대로 처리)
 *       > AuthenticationManager(인증처리) > UserDetailsService(사용자정보조회)
 *       > SecurityContext(인증결과저장) > Controller 도달
 *
 * [비교]                     세션         vs      JWT 적용 차이
 * 필터                       기본필터             JwtFilter 직접추가
 * 인증정보저장                  서버세션           SecurityContext에만
 * UserDetailsService       세션 생성시 1회       매요청마다 호출
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                //프론트 분리
                .formLogin(form ->form.disable())
                .httpBasic(httpBasic ->httpBasic.disable())

                //세션제어(중복로그인 시 기존 세션 만료)
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                        .expiredUrl("/login?expired"))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login","/api/register","/oauth2/**", "/login/oauth2/code/**",
                                "/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOAuth2UserService))
                        .defaultSuccessUrl("http://localhost:5173/",true)
                )

                /**
                 * [WHAT] 인증 안 된 요청 -> 401 반환처리
                 * [WHY] security는 ssr시절에 만든거라 인증없는요청은 HTML 로그인페이지로 리다이렉트되도록 설정
                 *      -> 요즘은 restapi+react로 분리되어있어서 프론트가 다른서버에 있어서 cors에러 발생
                 * : 프론트의 응답인터셉터 활용해서 처리할 것
                 */
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authExcception)->{
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401반환
                        })
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * [흐름]
     * authenticationManager.authenticate(ID/PW 평문)
     *         ↓
     * UserDetailsService에서 DB 유저 조회
     *         ↓
     * DB에 저장된 암호화된 비밀번호 꺼냄
     *         ↓
     * PasswordEncoder.matches(평문, 암호화된값) 자동 실행
     *         ↓
     * 일치 → Authentication 반환
     * 불일치 → BadCredentialsException
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
