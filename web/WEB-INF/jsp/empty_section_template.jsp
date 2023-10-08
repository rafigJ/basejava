<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" language="java" %>

<c:if test="${param.type == 'EXPERIENCE' || param.type == 'EDUCATION'}">
    <input type='text' style="font-size: 16px;"  name='${param.type}_companyName0' size='30' value='' placeholder='Название Организации'> </br>
    <input type='text' style="font-size: 16px;"  name='${param.type}_webSite0' size='50' value='' placeholder='Сайт Организации'> </br>
    <input type='text' name='${param.type}_endDate00' size='10' value='' placeholder='Дата конца'></br>
    <input type='text' name='${param.type}_periodTitle00' size='100' value='' placeholder='Заголовок'></br>
    <textarea name='${param.type}_periodDescription00' rows='5' cols='100' placeholder='Описание'></textarea></br>
</c:if>
<c:if test="${param.type != 'EXPERIENCE' && param.type != 'EDUCATION'}">
    <textarea name='${param.type}' placeholder='${SectionType.valueOf(param.type).title}' rows='5' cols='100'></textarea>
</c:if>
<div style="margin-bottom: 30px;"></div>
