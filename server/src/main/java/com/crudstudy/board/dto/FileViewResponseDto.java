package com.crudstudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
public class FileViewResponseDto {
    private Long fileId;
    private Resource resource; //로컬용
    private String url;         //cloudinary용
    private String fileName;
    private String resourceType;

    // 로컬 생성자
    public FileViewResponseDto(Long fileId, Resource resource, String fileName, String resourceType) {
        this.fileId = fileId;
        this.resource = resource;
        this.fileName = fileName;
        this.resourceType = resourceType;
    }

    // Cloudinary 생성자
    public FileViewResponseDto(Long fileId, String url, String fileName, String resourceType) {
        this.fileId = fileId;
        this.url = url;
        this.fileName = fileName;
        this.resourceType = resourceType;
    }
}
