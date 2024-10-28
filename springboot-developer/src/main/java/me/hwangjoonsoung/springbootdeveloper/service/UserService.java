package me.hwangjoonsoung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.domain.User;
import me.hwangjoonsoung.springbootdeveloper.dto.AddUserRequest;
import me.hwangjoonsoung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest user){
        return userRepository.save(User.builder().email(user.getEmail()).password(user.getPassword()).build()).getId();
    }

}
