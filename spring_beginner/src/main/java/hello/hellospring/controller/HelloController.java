package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.ref.ReferenceQueue;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc (@RequestParam(value = "name",required = false) String name , Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }


    @GetMapping("hello-api")
    @ResponseBody
    public String helloString (@RequestParam(value = "name",required = false) String name){
        return "hello-api : "+name;
    }

    @GetMapping("hello-api2")
    @ResponseBody
    public Hello helloApi(@RequestParam("name")String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }


}
