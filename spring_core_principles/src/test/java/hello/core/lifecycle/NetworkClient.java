package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {

    private static String URL;

    public NetworkClient() {
/*        System.out.println("생성자 호출URL = " + URL);
        connect();
        call("최기화 연결 메시지");*/
    }

    public void setURL(String URL) {
        NetworkClient.URL = URL;
    }

    //서비스 시작

    public void connect(){
        System.out.println("connect = " + URL);
    }

    public void call(String message){
        System.out.println("URL = " + URL + " message = " + message);
    }

    public void disConnect(){
        System.out.println("disconnect = " + URL);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //의존 관계 주입이 끝난 시첨
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        disConnect();
//    }

    @PostConstruct
    public void init() throws Exception {
        //의존 관계 주입이 끝난 시첨
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void destroy() throws Exception {
        disConnect();
    }
}
