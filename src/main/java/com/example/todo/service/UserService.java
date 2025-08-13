package com.example.todo.service;

import com.example.todo.domain.User;
import com.example.todo.dto.UserRequestDto;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * UserService는 사용자와 관련된 비즈니스 로직을 처리하는 서비스.
 * - UserController로부터 전달받은 데이터를 처리하고 결과를 반환.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     * - 사용자 정보를 받아 회원가입을 처리.
     * - 비밀번호는 암호화하여 저장.
     *
     * @param requestDto 사용자 정보 DTO
     * @return 회원가입 결과 메시지
     */
    public String  createUser(UserRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        /** passwordEncoder는 BCryptPasswordEncoder 객체.
         * encode()는 비밀번호를 암호화(해싱) 해주는 메서드.
         * 암호화된 비밀번호를 encodedPassword 변수에 저장.

         */
        User user = new User(requestDto.getUsername(), encodedPassword);
        userRepository.save(user); // ★ DB에 저장만 하고 반환값은 메시지로
        return  "회원가입 완료";
    }

    /**
     * 로그인 처리
     * - 사용자 정보를 받아 로그인 처리.
     * - 입력된 비밀번호와 저장된 비밀번호를 비교하여 인증.
     *
     * @param requestDto 사용자 정보 DTO
     * @return 로그인 결과 메시지
     */
    public String login(UserRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return "로그인 성공";
    }
    // 전체 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 수정
    public User updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(user);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

