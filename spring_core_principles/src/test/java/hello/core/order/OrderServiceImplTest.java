package hello.core.order;

import hello.core.discount.FixediscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {

    @Test
    void createOrder(){
        Member member = new Member(1L, "hwnag", Grade.VIP);

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(member);
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixediscountPolicy());
        Order order = orderService.createOrder(1L, "hwnag", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}