package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

public class MemberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

        Member hwang = new Member(1L, "hwang", Grade.VIP);
        memberService.join(hwang);

        Member byId = memberService.findById(1L);
        System.out.println(byId.getName());
        System.out.println(hwang.getName());

    }
}
