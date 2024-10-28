package me.hwangjoonsoung.springbootdeveloper.dto;

import lombok.Getter;

@Getter
public class AddUserRequest {
    private String email;
    private String password;
}
