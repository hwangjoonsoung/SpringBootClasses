package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {

    MemberService service;
    MemoryMemberRepository repository;

    @BeforeEach
    public void beforeEach() {
        this.repository = new MemoryMemberRepository();
        this.service = new MemberService(repository);
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hwnag");

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
        member.setName("hwang");

        Member member2 = new Member();
        member2.setName("hwang");

        //Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        service.join(member);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> service.join(member2));
        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }


    @Test
    void finMembers() {
        Member member = new Member();
        member.setName("hwang");
        service.join(member);

        Member member2 = new Member();
        member2.setName("kim");
        service.join(member2);

        List<Member> members = service.finMembers();
        int size = members.size();
        assertThat(size).isEqualTo(2);

    }

    @Test
    void findOne() {
    }
}