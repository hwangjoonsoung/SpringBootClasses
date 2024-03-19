package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBena(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "hwang", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixediscountPolicy");

        Assertions.assertThat(discountService).isInstanceOf(discountService.getClass());
        Assertions.assertThat(discountPrice).isEqualTo(1000);

        int rateDiscount = discountService.discount(member, 10000, "rateDiscountPolicy");
        Assertions.assertThat(rateDiscount).isEqualTo(1000);
    }

    static class  DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> discountPolicies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> discountPolicies) {
            this.policyMap = policyMap;
            this.discountPolicies = discountPolicies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("discountPolicies = " + discountPolicies);
        }

        public int discount(Member member, int i, String discountPolicies) {
            DiscountPolicy discountPolicy = policyMap.get(discountPolicies);
            int discount = discountPolicy.discount(member, i);
            return discount;
        }
    }


}
