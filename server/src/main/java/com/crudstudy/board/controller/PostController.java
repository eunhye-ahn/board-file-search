package com.crudstudy.board.controller;

import com.crudstudy.board.domain.Post;
import com.crudstudy.board.dto.PostRequestDto;
import com.crudstudy.board.service.FileService;
import com.crudstudy.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    /**
     * [WHAT] @RequestPart : multipart/form-data(혼합데이터) 요청에서
     *          각 파트를 개별로 받는 어노테이션
     * @RequestBody와 달리 파일(바이너리) + JSON 데이터를 함께 받을 수 있음
     *
     * @param request (application/json)
     * @param files (multipart/form-data)
     *              -> required=false : NPE 방지 로직 필수 구현
     */
    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(
            @RequestPart PostRequestDto request,
            @RequestPart(required = false) List<MultipartFile> files
            ) {
        System.out.println("files : " + files);
        Post post = postService.save(request,files);
        fileService.uploadFiles(post,files);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .build();
    }
}
