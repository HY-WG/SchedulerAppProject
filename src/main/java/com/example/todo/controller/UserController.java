package com.example.todo.controller;


import com.example.todo.domain.User;
import com.example.todo.dto.UserRequestDto;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * UserController는 사용자와 관련된 HTTP 요청을 처리하는 컨트롤러.
 * - 회원가입과 로그인 기능을 제공.
 * - 클라이언트의 요청을 받아 Service에 전달하고, 그 결과를 반환.
 */

@RestController // REST API 컨트롤러임을 나타냄 (JSON 응답)
@RequiredArgsConstructor // Lombok 기능: 생성자를 자동으로 만들어줌. @Autowired 대신 사용 가능. 의존성 주입 편리하게 gka.
@RequestMapping("/users") // API URL 앞부분 고정(prefix) 설정 (URL 기본 경로 설정)
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     * - POST /users
     * - 요청 본문(JSON)을 UserRequestDto로 변환
     * - Service에서 사용자 생성 후, 생성된 User 객체 반환
     */

    @PostMapping // POST /users/signup
    public String  createUser(@RequestBody UserRequestDto dto) {
        // @RequestBody: JSON → DTO 변환
        // @RequestBody → 클라이언트가 보낸 JSON을 DTO로 변환
        return userService.createUser(dto);
    }

    /**
     * 전체 사용자 조회 API
     * - GET /users
     * - DB에 저장된 모든 사용자 정보를 리스트로 반환
     */

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 사용자 수정 API
     * - PUT /users/{id}
     * - PathVariable로 사용자 ID를 받고, RequestBody로 수정할 정보 전달
     * - Service에서 사용자 정보 수정 후 수정된 User 객체 반환
     */

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userService.updateUser(id, dto);
    }
    /**
     * 사용자 삭제 API
     * - DELETE /users/{id}
     * - PathVariable로 사용자 ID를 받아 Service에서 삭제 처리
     */

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

