package com.crudstudy.board.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crudstudy.board.dto.FileUploadResult;
import com.crudstudy.board.exception.CustomException;
import com.crudstudy.board.exception.ErrorCode;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("cloudinary")
public class CloudinaryFileStorage implements FileStorage {

    private final Cloudinary cloudinary;

    public CloudinaryFileStorage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * [WHAT] cloudinary에 파일 저장
     * [흐름] 사용자 업로드 > spring이 multipartFile 객체에 담음
     *          > 바이트로 변환 (cloudinary sdk가 바이트를 원함)
     *          > cloudinary.uploader().upload(바이트로 변경된 파일, 파일종류);
     *          > resource_type이 auto면 자동감지
     */
    @Override
    public FileUploadResult save(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();

            Map<String, Object> option = new HashMap<>();
            option.put("resource_type", "auto");
            Map result = cloudinary.uploader().upload(fileBytes, option);

            String publicId = result.get("public_id").toString();
            String secureUrl = result.get("secure_url").toString();

            return new FileUploadResult(publicId, secureUrl);

        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public void delete(String filename) {

    }

    @Override
    public String getUrl(String filename) {
        return "";
    }
}
