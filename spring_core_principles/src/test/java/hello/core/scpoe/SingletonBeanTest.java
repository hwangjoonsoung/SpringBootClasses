package hello.core.scpoe;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class SingletonBeanTest {

    @Test
    public void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean2 = " + singletonBean2);
        System.out.println("singletonBean = " + singletonBean);
        Assertions.assertThat(singletonBean2).isSameAs(singletonBean);
        Assertions.assertThat(singletonBean2).isInstanceOf(singletonBean.getClass());
//        ac.close();

    }

    @Component
    @Scope("singleton")
    static class SingletonBean{

        @PostConstruct
        void init(){
            System.out.println("singleton Bean");
        }
        @PreDestroy
        void close(){
            System.out.println("singleton bean close");
        }
    }
}
