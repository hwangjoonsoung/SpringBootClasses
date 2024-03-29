package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }


    @Test
    void createOrder(){

        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "hwang", Grade.VIP);
        memberService.join(member);

        //when
        Order itemA = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(itemA.getDiscountPrice()).isEqualTo(1000);
        Assertions.assertThat(member.getId()).isEqualTo(itemA.getMemberId());
    }
}