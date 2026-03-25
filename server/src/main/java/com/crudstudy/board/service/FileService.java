package com.crudstudy.board.service;

import com.crudstudy.board.domain.File;
import com.crudstudy.board.domain.Post;
import com.crudstudy.board.dto.FileUploadResult;
import com.crudstudy.board.dto.PostRequestDto;
import com.crudstudy.board.repository.FileRepository;
import com.crudstudy.board.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 파일 업로드/삭제 비즈니스 로직 처리 (저장하기 전)
 *
 * [WHAT] MultipartFile : 클라이언트가 파일을 업로드할 때 Spring이 파일을 담아주는 객체
 *
 * getOriginalFilename     원본파일명
 * getSize                  파일크기
 * getContentType           파일타입
 * getBytes                 실제 바이너리 데이터
 * getInputStream           파일 읽기용 스트림 -> 실제 파일을 저장소에 복사할 때 사용
 *                             Files.copy(multipartFile.getInputStream(), targetPath)
 * isEmpty                  파일이 비었는지
 *
 * 위의 정보를 얻기위해 MultipartFile 객체로 변환해서 저장하는 것
 *
 * [흐름] 클라이언트 파일 선택
 *           > HTTP 요청에 바이너리로 담김 (multipart/form-data)
 *           > Spring이 MultipartFile로 변환
 *           > FileStorage에서 getInputStream()으로 저장소에 복사
 *           > FileService에서 파일 정보 DB 저장
 */

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileStorage fileStorage;
    private final FileRepository fileRepository;

    //여러 파일 처리
    public void uploadFiles(Post post, List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                uploadFile(post, file);
            }
        }
    }
    public void uploadFile(Post post, MultipartFile file) {
        FileUploadResult result = fileStorage.save(file);

        File fileEntity = File.builder()
                .post(post)
                .originalName(file.getOriginalFilename())
                .storedName(result.getStoredName())
                .filePath(result.getFilePath())
                .fileSize(file.getSize())
                .build();
        fileRepository.save(fileEntity);

    }
}
