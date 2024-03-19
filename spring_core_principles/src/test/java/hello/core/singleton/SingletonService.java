package hello.core.singleton;

public class SingletonService {

    private static final SingletonService getInstance = new SingletonService();

     public static SingletonService getInstance(){
         return getInstance;
     }

    private SingletonService() {
    }
}
