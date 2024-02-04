package hello.hellospring.service;

import hello.hellospring.domain.Member;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Fail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Optional;


class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hwnag");

        //when
        Long join = memberService.join(member);

        //then
        Member one = memberService.findOne(join).get();
        assertThat(join).isEqualTo(one.getId());
        assertThat(member.getName()).isEqualTo(one.getName());
    }

    @Test
    public void exceptionTest() {
        Member member = new Member();
        member.setName("hwang");

        Member member2 = new Member();
        member2.setName("hwang");

        //Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }


    @Test
    void finMembers() {
    }

    @Test
    void findOne() {
    }
}