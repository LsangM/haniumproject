package haniumgpt.haniumbackend.security;

import haniumgpt.haniumbackend.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {
    private static final String SECRET_KEY = "NMA8JPctFuna5";

    public String create(UserEntity userEntity){
        //JWT Token 생성
        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 하기 위한 secret-key
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(1,ChronoUnit.DAYS)))
                .compact();
    }

    public String validateAndGetUserId(String token){
        //parseClaimJws 메서드가 Base 64로 디코딩 및 파싱
        // 헤더와 페이로드를 setsigningkey로 넘어온 시크릿을 이용해 서명 후 token의 서명과 비교
        //위조 되지 않았다면 페이로드 (claim) 리턴, 위조라면 예외 처리
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}

