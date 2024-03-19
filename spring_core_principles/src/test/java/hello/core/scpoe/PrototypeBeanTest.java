package hello.core.scpoe;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeBeanTest {
    @Test
    void findBean(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean2 = " + prototypeBean2);
        System.out.println("prototypeBean = " + prototypeBean);
        Assertions.assertThat(prototypeBean2).isNotSameAs(prototypeBean);
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{

        @PostConstruct
        void init(){
            System.out.println("prototype Bean");
        }
        @PreDestroy
        void close(){
            System.out.println("prototype bean close");
        }
    }
}
