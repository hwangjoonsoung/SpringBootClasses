package hello.core.scpoe;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeBena(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
        prototypeBean.addCount();
        Assertions.assertThat(prototypeBean.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

        Assertions.assertThat(prototypeBean).isNotSameAs(prototypeBean2);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class , ClientBean.class);
        ClientBean clientBean = ac.getBean(ClientBean.class);
        int logic = clientBean.logic();

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        Assertions.assertThat(logic).isEqualTo(1);
        Assertions.assertThat(logic2).isEqualTo(1);
//        Assertions.assertThat(clientBean).isSameAs(clientBean2);
    }

    @Scope("singleton")
    static class ClientBean{

//        private final PrototypeBean prototypeBean; //생성 시점에 주입되어 있음

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        private final Provider<PrototypeBean> provider;

        @Autowired
        private AnnotationConfigApplicationContext annotationConfigApplicationContext;

/*        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }*/

        @Autowired
        public ClientBean(Provider<PrototypeBean> provider) {
            this.provider = provider;
        }

        //        @Autowired
//        ApplicationContext applicationContext;



        public int logic(){
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
/*        public PrototypeBean logic2(){
            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
            return prototypeBean;
        }*/
    }


    @Scope("prototype")
    static class PrototypeBean{
        private int count =0;

        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        void init(){
            System.out.println("PrototypeBean.init : "+ this);
        }


        @PreDestroy
        void destory(){
            System.out.println("PrototypeBean.destory");
        }
    }
}
