package hello.servlet.web.frontcontroller.v4.contoller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    MemberRepository memberRepository = MemberRepository.getInstace();

    @Override
    public String Process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> all = memberRepository.findAll();

        model.put("members", all);
        return "members";
    }

}
