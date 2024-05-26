package hello.servlet.web.frontcontroller.v4.contoller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String Process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
