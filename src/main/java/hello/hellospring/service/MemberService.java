package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {


    private final MemberRepository repository ;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        repository = memberRepository;
    }


    public Long join(Member member){
        Optional<Member> byName = repository.findByName(member.getName());
//        if (byName.isPresent()){
//            throw new IllegalStateException();
//        }
        byName.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        repository.save(member);
        return member.getId();
    }

    public List<Member> finMembers(){
        return repository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return repository.findById(memberId);
    }

}
