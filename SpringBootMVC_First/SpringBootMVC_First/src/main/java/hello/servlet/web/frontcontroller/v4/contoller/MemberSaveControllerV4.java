package hello.servlet.web.frontcontroller.v4.contoller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    MemberRepository memberRepository = MemberRepository.getInstace();

    @Override
    public String Process(Map<String, String> paramMap, Map<String, Object> model) {
        String name = paramMap.get("username").toString();
        int age = Integer.parseInt(paramMap.get("age").toString());

        Member member = new Member(name, age);
        memberRepository.save(member);
        model.put("member", member);

        return "save-result";

    }

}
