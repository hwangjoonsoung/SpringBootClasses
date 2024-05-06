<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %><%--
  Created by IntelliJ IDEA.
  User: adminmaster
  Date: 2024-05-06
  Time: 오후 6:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstace();
    Member member = new Member();
    member.setUsername(request.getParameter("username"));
    member.setAge(Integer.parseInt(request.getParameter("age")));

    memberRepository.save(member);
%>

<html>
<head>
    <title>title</title>
</head>

<body>
성공
<ul>
    <li>id = <%=member.getId()%>
    </li>
    <li>username = <%=member.getUsername()%>
    </li>
    <li>age = <%=member.getAge()%>
    </li>
</ul>

</body>
</html>

