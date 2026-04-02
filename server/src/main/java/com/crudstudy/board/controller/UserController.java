package com.crudstudy.board.controller;

import com.crudstudy.board.dto.LoginRequestDto;
import com.crudstudy.board.dto.RegisterRequestDto;
import com.crudstudy.board.service.AuthService;
import com.crudstudy.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request, HttpServletRequest servletRequest){
        userService.register(request);
        authService.login(new LoginRequestDto(request.getEmail(),request.getPassword()), servletRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
