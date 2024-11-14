package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //서블릿 자동 생성
@SpringBootApplication
public class SpringBootMvcFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcFirstApplication.class, args);
    }

}
