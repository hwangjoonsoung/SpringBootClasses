package me.hwangjoonsoung.springbootdeveloper.service;

import me.hwangjoonsoung.springbootdeveloper.dto.Member;
import me.hwangjoonsoung.springbootdeveloper.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAllMember(){
        return memberRepository.findAll();
    }

}
