package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.module.ResolutionException;

@WebServlet(urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-Control", "no-Cache , no-store , must-revalidate");
        resp.setHeader("Pragma", "no-Cache");
        resp.setHeader("my-header", "hello");

//        content(resp);
//        cookie(resp);
        redirect(resp);


        PrintWriter writer = resp.getWriter();
        writer.print("ok");

    }

    private void content(HttpServletResponse response) {

        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }

    private void cookie(HttpServletResponse response){
        Cookie cookie = new Cookie("mycookie", "good");
        cookie.setMaxAge(600);
        response.addCookie(cookie);

    }

    private void redirect(HttpServletResponse response) throws IOException {

        // 1번 방법
//        response.setStatus(HttpServletResponse.SC_FOUND);
//        response.setHeader("Location", "basic/hello-form.html");

        // 2번 방법
        response.sendRedirect("basic/hello-form.html");

    }
}
