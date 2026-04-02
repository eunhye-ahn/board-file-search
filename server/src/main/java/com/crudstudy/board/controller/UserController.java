package com.crudstudy.board.controller;

import com.crudstudy.board.dto.RegisterRequestDto;
import com.crudstudy.board.service.UserService;
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

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request){
        userService.register(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
