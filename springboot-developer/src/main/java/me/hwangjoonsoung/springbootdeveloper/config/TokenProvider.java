package me.hwangjoonsoung.springbootdeveloper.config;

import io.jsonwebtoken.*;
import jakarta.persistence.MapKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import me.hwangjoonsoung.springbootdeveloper.domain.User;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();

        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //토큰 생성
    private String makeToken(Date expriy, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //header type 설정
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) //token 생성시간 = 현재시간
                .setExpiration(expriy) // token 만료 시간
                .setSubject(user.getEmail()) // token 내용
                .claim("id", user.getId()) // 클레임 id = 유저 id
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // token 암호화
                .compact();
    }

    //토큰 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 인정 정보 가져오기
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authoritise = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(), "", authoritise)
                , token
                , authoritise);
    }

    // 토큰에 있는 userId 가져오기
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        Long id = claims.get("id", Long.class);
        return id;
    }

    // 클레임 조회 (토큰의 내용)
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
