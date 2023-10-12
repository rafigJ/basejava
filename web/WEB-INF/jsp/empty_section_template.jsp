<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" language="java" %>

<c:if test="${param.type == 'EXPERIENCE' || param.type == 'EDUCATION'}">
    <h3>${SectionType.valueOf(param.type).title}</h3><br>
    <c:import url="fragments/empty_company.jsp">
        <c:param name="type" value="${param.type}"/>
        <c:param name="i" value="${0}"/>
    </c:import>
</c:if>

<c:if test="${param.type == 'ACHIEVEMENT' || param.type == 'QUALIFICATIONS'}">
    <table>
        <tr>
            <td width="200">${SectionType.valueOf(param.type).title}</td>
            <td>
                <textarea name='${param.type}' placeholder="Для маркированного текста используйте enter" rows='5'
                          cols='100'></textarea>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${param.type == 'PERSONAL' || param.type == 'OBJECTIVE'}">
    <table>
        <tr>
            <td width="200">${SectionType.valueOf(param.type).title}</td>
            <td>
                <textarea name='${param.type}' placeholder="Введите текст" rows='4' cols='100'></textarea>
            </td>
        </tr>
    </table>
</c:if>
<div style="margin-bottom: 30px;"></div>
