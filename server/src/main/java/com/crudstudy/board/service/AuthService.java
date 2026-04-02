package com.crudstudy.board.service;

import com.crudstudy.board.dto.LoginRequestDto;
import com.crudstudy.board.exception.CustomException;
import com.crudstudy.board.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    //login
    public void login(LoginRequestDto request) {
        //인증처리(UserDetailsService가 DB에서 유저 조회 + 비밀번호 대조)
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            //securityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException e){
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        } catch (UsernameNotFoundException e){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    //logiout
}
