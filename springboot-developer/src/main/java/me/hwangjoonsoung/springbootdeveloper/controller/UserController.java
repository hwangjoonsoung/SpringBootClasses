package me.hwangjoonsoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.springbootdeveloper.dto.AddUserRequest;
import me.hwangjoonsoung.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request);
        return "redurect:/login";
    }

}
