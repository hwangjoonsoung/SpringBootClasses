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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.reactive.TransactionalOperatorExtensionsKt;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

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
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Duration.ofDays(7).toMillis());

        JwtFactory jwtFactory = JwtFactory.builder().expiration(expirationDate).build();
        String token = jwtFactory.createToken(jwtProperties);
        //when
        boolean isValid = tokenProvider.validToken(token);
        //then
//        Assertions.assertThat(isValid).isTrue();

        //이게 정상적인 test방법일까?
        //2개로 나눠야 하는거 아닌가?
        //expiration에 날짜의 변하는 것 처럼 자동으로 test는 돌아가야 하는거 아닌가?

        if (now.after(expirationDate)){
            Assertions.assertThat(isValid).isFalse();
        }else{
            Assertions.assertThat(isValid).isTrue();
        }

    }

    @Test
    @DisplayName("유효한 토큰인 경우 유효성 검증에 성공")
    void validToken_validToken() throws Exception {
        //given
        String token = JwtFactory.withDefaultValue().createToken(jwtProperties);
        //when
        boolean isValid = tokenProvider.validToken(token);

        //then
        Assertions.assertThat(isValid).isTrue();

    }

    @Test
    @DisplayName("토큰 기반으로 인증 정보를 가져올 수 있음")
    void getAuthentication() throws Exception {
        //given
        String userEmail = "joonsoung93@naver.com";
        String token = JwtFactory.builder().subject(userEmail).build().createToken(jwtProperties);

        //when
        Authentication authentication = tokenProvider.getAuthentication(token);

        //then
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        String username = principal.getUsername();
        Assertions.assertThat(userEmail).isEqualTo(username);
    }

    @Test
    @DisplayName("토큰으로 유저의 ID를 가져올 수 있다.")
    void getUserID() throws Exception {
        Long userId = 1L;

        //given
        String token = JwtFactory.builder().claims(Map.of("id", userId)).build().createToken(jwtProperties);

        //when
        Long userId1 = tokenProvider.getUserId(token);

        //then
        Assertions.assertThat(userId1).isEqualTo(userId);

    }
}
