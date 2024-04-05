package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //GET
    //uri /hello-world (end point)
    //기존에는 @RequestMapping을 사용함
    //@RequestMapping는 @ReuqsetMapping(method , path)를 지정해 줘야 한다.
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello world");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("hello world , %s ", name));
    }

    @GetMapping("/hello-world-bean/path-variable2/{differentName}")
    public HelloWorldBean helloWorldBean2(@PathVariable(value = "differentName") String name) {
        return new HelloWorldBean("hello world with variable2: " + name);
    }

    @GetMapping("hello-world-internationalized")
    public String helloworldInternalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message" , null , locale);
    }

}
