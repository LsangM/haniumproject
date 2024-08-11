package haniumgpt.haniumbackend.service;

import haniumgpt.haniumbackend.model.UserEntity;
import haniumgpt.haniumbackend.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// 테스트용 계정 (추후 삭제 예정)
@Profile("!prod")
@Component
public class TestAccount implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("han@naver.com")) {
            UserEntity testUser = UserEntity.builder()
                    .email("han@naver.com")
                    .password("han0000")
                    .job("software developer")
                    .interestedField("IT")
                    .englishProficiency("Intermediate proficiency")
                    .build();

            userRepository.save(testUser);
            System.out.println("테스트 계정 생성, 위치 : Service/TestAccount");
        }
    }
}