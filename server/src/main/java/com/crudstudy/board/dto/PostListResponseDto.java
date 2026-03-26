package com.crudstudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResponseDto {
    private String title;
    private LocalDateTime createdAt;
    private int viewCount;
    private List<FileDownloadResponseDto> files;
}
