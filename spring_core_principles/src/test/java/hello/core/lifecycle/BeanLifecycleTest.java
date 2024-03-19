package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BeanLifecycleTest {

    @Test
    public void lifeCycleTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        LifeCycleConfig bean = ac.getBean(LifeCycleConfig.class);
        ac.close();
    }


    static class LifeCycleConfig{

        @Bean(initMethod = "init" , destroyMethod = "destroy")
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setURL("http://naver.com");
            return networkClient;
        }

    }

}
