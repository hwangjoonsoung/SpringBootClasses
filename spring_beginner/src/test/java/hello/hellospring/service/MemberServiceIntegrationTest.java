package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService service;
    @Autowired
    MemberRepository repository;

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("jang");

        //when
        Long join = service.join(member);

        //then
        Member one = service.findOne(join).get();
        assertThat(join).isEqualTo(one.getId());
        assertThat(member.getName()).isEqualTo(one.getName());
    }

    @Test
    public void exceptionTest() {
        Member member = new Member();
        member.setName("Lee");

        Member member2 = new Member();
        member2.setName("Lee");

        //Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        service.join(member);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> service.join(member2));
        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }


    @Test
    void finMembers() {
        Member member = new Member();
        member.setName("hwang2");
        service.join(member);

        Member member2 = new Member();
        member2.setName("kim2");
        service.join(member2);

        List<Member> members = service.finMembers();
        int size = members.size();
        assertThat(size).isEqualTo(9);

    }

    @Test
    void findOne() {
    }
}