package me.hwangjoonsoung.springbootdeveloper.controller;

import io.jsonwebtoken.Jwts;
import me.hwangjoonsoung.springbootdeveloper.config.JwtProperties;
import me.hwangjoonsoung.springbootdeveloper.config.TokenProvider;
import me.hwangjoonsoung.springbootdeveloper.domain.User;
import me.hwangjoonsoung.springbootdeveloper.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Date;

@SpringBootTest
public class TokenTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;


    @Test
    @DisplayName("토근 검증")
    void generateToken() throws Exception {
        //given
        User testUser = userRepository.save(User.builder().email("joonsoung@naver.com").password("1111qqqq").build());

        //when
        String newToken = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        //then
        Long userId = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(newToken).getBody().get("id", Long.class);

        Assertions.assertThat(testUser.getId()).isEqualTo(userId);

    }

    @Test
    @DisplayName("토큰 유효성 검사")
    void invaildToken() throws Exception {
        //given
        JwtFactory jwtFactory = JwtFactory.builder().expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis())).build();
        String token = jwtFactory.createToken(jwtProperties);
        System.out.println("IssuedAt = " + jwtFactory.getIssuedAt());
        System.out.println("Expiration = " + jwtFactory.getExpiration());
        System.out.println("token = " + token);
        //when
        boolean isValid = tokenProvider.validToken(token);
        //then
        Assertions.assertThat(isValid).isFalse();
    }
}
