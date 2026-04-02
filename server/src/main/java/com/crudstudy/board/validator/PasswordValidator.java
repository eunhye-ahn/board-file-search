package com.crudstudy.board.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * [WHAT] 커스텀 어노테이션의 검증로직 클래스
 * [흐름] return false -> 검증실패시 message 전달
 *       return true -> 통과
 */

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext conntext) {
        if(password == null || password.length() < 8 ) return false;

        int count = 0;
        if(password.matches(".*[A-Z].*")) count++;
        if(password.matches(".*[a-z].*")) count++;
        if(password.matches(".*[0-9].*")) count++;
        if(password.matches(".*[^a-zA-Z0-9].*")) count++;

        return count >= 3;
    }
}
