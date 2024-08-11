package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.dto.ResponseDto;
import haniumgpt.haniumbackend.dto.UserDto;
import haniumgpt.haniumbackend.model.UserEntity;
import haniumgpt.haniumbackend.security.TokenProvider;
import haniumgpt.haniumbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    // private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private PasswordEncoder passwordEncoder;


    //회원 가입(중복 조사 후 회원 저장)
    @PostMapping("/SignUp")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        try {
            UserEntity user = UserDto.toEntity(userDto);
            UserEntity registerUser = userService.create(user);
            UserDto responseUserDto = new UserDto(registerUser);

            return ResponseEntity.ok().body(responseUserDto);
        }catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/LogIn")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto){
        UserEntity user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword(), passwordEncoder);

        if (user == null){
            ResponseDto responseDto = ResponseDto.builder().error("Login failed.").build();
            return ResponseEntity.badRequest().body(responseDto);
        }

        final String token = tokenProvider.create(user);
        final UserDto responseUserDto = new UserDto(user,token);
        return ResponseEntity.ok().body(responseUserDto);
    }

    // 로그인된 사용자 정보 조회 (토큰은 헤더로 전달)
    @GetMapping("/Profile")
    public ResponseEntity<?> getLoggedInUserInfo(@RequestHeader("Authorization") String token) {
        String userId = getUserIdFromToken(token);
        UserEntity user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(new UserDto(user));
    }

    // 로그인된 사용자 정보 수정
    // dto 속성 : (전부 필수 아님) mail, password, job, interestedField, englishProficiency
    @PutMapping("/Profile")
    public ResponseEntity<?> updateLoggedInUserInfo(@RequestHeader("Authorization") String token, @RequestBody UserDto userDto) {
        String userId = getUserIdFromToken(token);
        UserEntity updatedUser = userService.updateUser(userId, userDto);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user");
        }
        return ResponseEntity.ok(new UserDto(updatedUser));
    }

    private String getUserIdFromToken(String token) {
        return tokenProvider.validateAndGetUserId(token);
    }
}
