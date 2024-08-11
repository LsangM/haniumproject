package haniumgpt.haniumbackend.service;

import haniumgpt.haniumbackend.dto.UserDto;
import haniumgpt.haniumbackend.model.UserEntity;
import haniumgpt.haniumbackend.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //controller에서 회원 가입 시 중복 방지 및 형식 확인
    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getEmail()==null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists {}",email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }

    //로그인 시, 아이디와 로그인 확인.
    public UserEntity getByCredentials(final String email, final String password,final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByEmail(email);
        String encodedPassword = encoder.encode(password);

        if(originalUser != null && encoder.matches(originalUser.getPassword(),encodedPassword)){
            return originalUser;
        }
        return null;
    }

    public UserEntity findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity updateUser(String id, UserDto userDto) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEmail(userDto.getEmail());
            user.setJob(userDto.getJob());
            user.setInterestedField(userDto.getInterestedField());
            user.setEnglishProficiency(userDto.getEnglishProficiency());
            userRepository.save(user);
        }
        return user;
    }
}
