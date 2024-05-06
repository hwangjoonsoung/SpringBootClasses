package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstace();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void newMember() throws Exception{
        //given
        Member hello = new Member("hello", 20);

        //when
        Member saved = memberRepository.save(hello);

        //then
        Member findOne = memberRepository.findOne(saved.getId());

        Assertions.assertThat(saved).isEqualTo(findOne);

    }

    @Test
    public void allMember() throws Exception{
        //given
        Member hello1 = new Member("hello1", 10);
        Member hello2 = new Member("hello2", 20);
        Member hello3 = new Member("hello3", 30);
        Member hello4 = new Member("hello4", 40);
        Member hello5 = new Member("hello5", 50);

        //when
        memberRepository.save(hello1);
        memberRepository.save(hello2);
        memberRepository.save(hello3);
        memberRepository.save(hello4);
        memberRepository.save(hello5);

        //then
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(5);
        Assertions.assertThat(all).contains(hello1, hello2, hello3, hello4, hello5);
    }



}