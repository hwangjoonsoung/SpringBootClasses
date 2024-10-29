package me.hwangjoonsoung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.service.UserDetailService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화
    // 인증 인가를 적용하는 곳은 정적 리소스에는 적용할 필요가 없다.
    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring().requestMatchers(toH2Console()).requestMatchers("/static/**");
        /*
        * 트러블 슈팅
        * org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.boot.autoconfigure.h2.H2ConsoleProperties' available
        * ignoring하는 과정에서 h2 console이 안되는 문제를 발견하였다.
        * 따라서 임시 해결책으로 ignore하는 것 말고 누구나 접근 할 수 있도록 변경하였다.
        * */
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests()//인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user")
                .permitAll()//인증 인가 없이 누구나 접근 가능
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest()//위에서 설정한 url이외의 요청에 대해서 설정
                .authenticated()// 별도의 인가는 필요하지 않지만 인증이 접근 할 수 있다.
                .and()
                .formLogin()// 폼 기반으로 로그인 할 예정
                .loginPage("/login")// 폼 기반 로그인 설정
                .defaultSuccessUrl("/articles")// 로그인 성공시 이동하는 곳
                .and()
                .logout()// 로그아웃 설정
                .logoutSuccessUrl("/login")// 로그아웃 성공시 이동하는 곳
                .invalidateHttpSession(true)//sessiong 삭제
                .and()
                .csrf()//csrf 비활성화
                .disable()
                .build();
    }

    //인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http , BCryptPasswordEncoder bCryptPasswordEncoder , UserDetailService userDetailService) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)// 사용자 정보 서비스 설정(이때 해당 service UserDetailService를 상속받아야 한다.)
                .passwordEncoder(bCryptPasswordEncoder)// 비밀번호 암호화하기 위한 인코더 설정
                .and()
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
