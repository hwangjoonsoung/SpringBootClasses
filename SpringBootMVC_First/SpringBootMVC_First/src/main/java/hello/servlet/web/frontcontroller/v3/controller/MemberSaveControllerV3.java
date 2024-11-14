package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyVIew;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;
import java.util.Objects;

public class MemberSaveControllerV3 implements ControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstace();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String name = paramMap.get("username").toString();
        int age = Integer.parseInt(paramMap.get("age").toString());

        Member member = new Member(name, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);
        return modelView;
    }
}
