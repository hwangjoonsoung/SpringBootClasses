package me.hwangjoonsoung.springbootdeveloper.controller;

import me.hwangjoonsoung.springbootdeveloper.dto.Member;
import me.hwangjoonsoung.springbootdeveloper.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers(){
        List<Member> members = testService.getAllMember();
        return members;
    }

}
