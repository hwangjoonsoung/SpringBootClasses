package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMeberRepository implements MemberRepository{

    private final EntityManager entityManager;

    public JpaMeberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = entityManager.createQuery("select m from Member m where m.name = :name ", Member.class).setParameter("name", name).getResultList();
        return result.stream().findAny();
    }


    @Override
    public List<Member> findAll() {
        List<Member> query = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return query;
    }
}
