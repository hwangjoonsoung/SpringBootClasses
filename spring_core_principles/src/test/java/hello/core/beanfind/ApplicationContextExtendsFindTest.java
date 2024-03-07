package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixediscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("타입으로 조회하는 경우 중복 에러 발생")
    void finBeanByParentTypeDuplicate(){
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("타입으로 조회 했을때 중복이 있으면 getBean을 할때 빈 이름을 지정하면 됨")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회(좋지 않음)")
    void findBeanBySubTypeBeanName(){
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
    
    @Test
    @DisplayName("부모타입으로 전체 조회")
    void findAllBeanParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        for (String s : beansOfType.keySet()) {
            DiscountPolicy discountPolicy = beansOfType.get(s);
            org.assertj.core.api.Assertions.assertThat(discountPolicy).isInstanceOf(DiscountPolicy.class);
        }

    }


    @Configuration
    static class TestConfig{

        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixediscountPolicy();
        }
    }


}
