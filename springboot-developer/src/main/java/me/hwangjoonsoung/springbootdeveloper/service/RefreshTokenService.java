package me.hwangjoonsoung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.dto.RefreshToken;
import me.hwangjoonsoung.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Unexpected token"));

    }

}
