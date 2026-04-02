package com.crudstudy.board.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [WHAT] 커스텀어노테이션 정의
 *
 * [흐름] 요청 > 컨트롤러 @Valid작동 > 유효성검사 > 실패시
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    //검증실패시메시지
    String message() default "비밀번호 조건을 확인해주세요";
    //bean validation 규칙상 필수
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
