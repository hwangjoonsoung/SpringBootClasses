package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyVIew;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.contoller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.contoller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.contoller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "FrontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private Map<String, Object> handlerMappingMap = new HashMap<>();
    private List<MyHandlerAdapter> myHandlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();

        initHandlerAdapter();
    }

    private void initHandlerAdapter() {
        myHandlerAdapters.add(new ControllerV3HandlerAdapter());
        myHandlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV5.service");

        Object handler = handler(req);

        if (handler == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(req, resp, handler);

        String viewName = modelView.getViewName();

        MyVIew myVIew = viewResolver(viewName);
        myVIew.render(modelView.getModel(), req, resp);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        MyHandlerAdapter a;
        for (MyHandlerAdapter adapter : myHandlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("핸들러 정보를 찾을 수 없습니다 : " + handler);
    }

    private Object handler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        System.out.println("req = " + requestURI);
        Object handler = handlerMappingMap.get(requestURI);
        return handler;
    }

    private static MyVIew viewResolver(String viewName) {
        MyVIew myVIew = new MyVIew("/WEB-INF/views/" + viewName + ".jsp");
        return myVIew;
    }
}