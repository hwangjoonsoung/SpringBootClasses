package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("hwang");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member = new Member();
        member.setName("hwang");
        repository.save(member);

        Member member1 = new Member();
        member1.setName("Kim");
        repository.save(member);

        Member hwang = repository.findByName("hwang").get();
        assertThat(member).isEqualTo(hwang);
//        org.assertj.core.api.Assertions.assertThat(member1).isEqualTo(hwang); error

    }

    @Test
    public void findById() {
        Member member = new Member();
        member.setName("hwang");
        repository.save(member);

        Member member2 = new Member();
        member2.setName("King");
        repository.save(member);

        Member byId = repository.findById(member.getId()).get();

        assertThat(member).isEqualTo(byId);

    }

    @Test
    public void findAll() {
        Member member = new Member();
        member.setName("hwang");
        repository.save(member);

        Member member2 = new Member();
        member2.setName("King");
        repository.save(member);

        List<Member> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);

    }
}
