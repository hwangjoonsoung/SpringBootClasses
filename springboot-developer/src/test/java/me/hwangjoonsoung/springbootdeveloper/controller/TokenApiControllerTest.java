package me.hwangjoonsoung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.Snippet;
import me.hwangjoonsoung.springbootdeveloper.config.JwtProperties;
import me.hwangjoonsoung.springbootdeveloper.domain.User;
import me.hwangjoonsoung.springbootdeveloper.dto.CreateAccessTokenRequest;
import me.hwangjoonsoung.springbootdeveloper.dto.RefreshToken;
import me.hwangjoonsoung.springbootdeveloper.repository.RefreshTokenRepository;
import me.hwangjoonsoung.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("새로운 액세스 토큰을 발급")
    void createNewAccessToken() throws Exception {
        //given
        final String url = "/api/token";

        //when
        User user = userRepository.save(User.builder().email("joonsoung93@naver.com").password("test").build());

        String token = JwtFactory.builder().claims(Map.of("id", user.getId())).build().createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(user.getId(), token));

        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(token);

        final String requestBody = objectMapper.writeValueAsString(request);
        ResultActions perform = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

        //then
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

    }


}
