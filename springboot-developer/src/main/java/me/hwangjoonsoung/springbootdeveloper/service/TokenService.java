package me.hwangjoonsoung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.hwangjoonsoung.springbootdeveloper.config.TokenProvider;
import me.hwangjoonsoung.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        if (!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected Token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

}
