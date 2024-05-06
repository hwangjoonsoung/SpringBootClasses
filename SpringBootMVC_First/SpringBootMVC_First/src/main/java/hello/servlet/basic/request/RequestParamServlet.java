package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("전체 파라미터 조회");

        req.getParameterNames().asIterator().forEachRemaining(s -> {
            String parameter = req.getParameter(s);
            System.out.println("parameter = " + parameter);
        });

        System.out.println("단일 파라미터 조회");
        System.out.println("age : " +req.getParameter("age"));
        System.out.println("name : "+req.getParameter("username"));

        System.out.println("key가 같은 파라미터 조회");
        String[] parameterValues = req.getParameterValues("username");
        for (String parameterValue : parameterValues) {
            System.out.println(parameterValue);
        }

        resp.getWriter().print("ok");


    }
}
