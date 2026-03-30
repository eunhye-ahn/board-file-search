package com.crudstudy.board.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * [WHAT] yml에서 값을 꺼내와 Cloudinary 객체를 생성하는 Config
 * [WHY] 외부저장소를 사용하려면 객체 필요
 * [주의] Cloudinary 생성자 : public Cloudinary(Map config) { ... }
 *
 * [흐름] Cloudinary 객체 생성 후 bean등록
 *          -> 스토리지 구현체에서 bean을 주입받아 save delete getUrl 구현
 *          -> mvc에서 사용
 */

/**
 * Map : key - value 쌍으로 데이터를 저장하는 자료구조
 */
@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

    //Cloudinary SDK 제공 편의 메서드
//    @Bean
//    public Cloudinary cloudinary() {
//        return new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", cloudName,
//                "api_key", apiKey,
//                "api_secret", apiSecret,
//                "secure", true
//        ));
//    }
}
