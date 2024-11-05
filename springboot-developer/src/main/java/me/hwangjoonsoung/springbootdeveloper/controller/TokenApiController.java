package me.hwangjoonsoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.CreateAccessTokenResponse;
import me.hwangjoonsoung.springbootdeveloper.service.RefreshTokenService;
import me.hwangjoonsoung.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }
}
