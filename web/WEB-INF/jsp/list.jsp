<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" language="java" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlMapper" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div style="text-align: left;">
        <a href="resume?uuid=${resume.uuid}&action=add"><img width="35" height="35" src="img/add2.png"></a>
    </div>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=HtmlMapper.toHtml(ContactType.EMAIL, resume.getContact(ContactType.EMAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img width="20" height="20" src="img/delete2.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img width="20" height="20" src="img/pencil2.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>