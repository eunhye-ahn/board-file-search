package com.crudstudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileViewResponseDto {
    private Resource resource;
    private String fileName;
    private String ContentType;
}
