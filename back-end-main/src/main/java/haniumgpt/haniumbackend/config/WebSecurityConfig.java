package haniumgpt.haniumbackend.config;

import haniumgpt.haniumbackend.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@EnableWebSecurity
@Slf4j
@Configuration
public class WebSecurityConfig {
    private final long Max_AGE_SECS = 3600;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())//webMvcConfig에서 이미 설정했으니 기본 cors설정
                .csrf(csrf -> csrf.disable()) //csrf는 지금 사용하지 않으니 disable
                .httpBasic(httpBasic -> httpBasic.disable()) //token을 사용하므로 basic 인증 disable
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //session 기반이 아님을 선언
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers("/**", "/auth/**").permitAll()
                                .anyRequest().authenticated());
                        //.redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint.baseUri("/oauth2/callback/*"))
                        //.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuthUserService))); //OAuthUserServiceImpl를 유저 서비스로 등록
        // '/'와 '/auth/**' 이외의 모든 경로는 인증해야함.

        //http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class); //filter 등록
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); //접근을 허용하는 주소 리스트 작성

        configuration.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS")); //접근을 허용하는 메소드
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
