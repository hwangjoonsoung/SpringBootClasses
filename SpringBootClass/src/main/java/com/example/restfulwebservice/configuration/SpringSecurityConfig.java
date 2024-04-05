package com.example.restfulwebservice.configuration;

import com.example.restfulwebservice.dao.UserDaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SpringSecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails newUser = User.withUsername("user").password(passwordEncoder().encode("1111qqqq")).authorities("read").build();

        userDetailsService.createUser(newUser);
        return userDetailsService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/*"));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , HandlerMappingIntrospector handlerMappingIntrospector) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
