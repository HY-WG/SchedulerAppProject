package com.example.todo.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화 및 검증을 담당하는 클래스
 * - BCrypt 알고리즘을 사용하여 안전하게 비밀번호를 저장
 * - 스프링 빈(@Component)으로 등록되어 다른 클래스에서 주입 가능
 */

@Component
public class PasswordEncoder {

    /**
     * 비밀번호를 해시하여 암호화
     *
     * @param rawPassword 사용자 입력 비밀번호
     * @return 암호화된 문자열(해시)
     */

    public String encode(String rawPassword) {
        // BCrypt.MIN_COST: 최소 비용(cycle)으로 해시 생성 (빠르게 테스트 가능)
        // BCrypt.withDefaults(). 에 대해서는 개념 노트에서 개념 복습하도록.
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 입력 비밀번호와 저장된 암호화 비밀번호 비교
     *
     * @param rawPassword 사용자가 입력한 비밀번호
     * @param encodedPassword DB에 저장된 암호화된 비밀번호
     * @return 일치 여부(true/false)
     */

    public boolean matches(String rawPassword, String encodedPassword) {
        // BCrypt.verifyer()로 입력 비밀번호와 해시 비교
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified; // 일치하면 true, 아니면 false
    }
}