package com.crudstudy.board.service;

import com.crudstudy.board.domain.User;
import com.crudstudy.board.dto.RegisterRequestDto;
import com.crudstudy.board.exception.CustomException;
import com.crudstudy.board.exception.ErrorCode;
import com.crudstudy.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //register > 컨트롤러에 붙일때 자동로그인까지 고려
    @Transactional
    public void register(RegisterRequestDto request){
        //이메일유효성검사
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        //비밀번호암호화
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        //저장
        userRepository.save(user);
    }

    //updateProfile

    //deleteUser

    //getUser
}
