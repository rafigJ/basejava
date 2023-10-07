<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.type == 'EXPERIENCE' || param.type == 'EDUCATION'}">
    <input type='text' name='companyName' placeholder='Название организации' size='30' value=''> </br>
    <input type='text' name='webSite' placeholder='Ссылка на вебсайт' size='30' value=''> </br>
    <input type='text' name='startDate' placeholder='01/2023' size='10' value=''>
    <input type='text' name='endDate' placeholder='02/2023' size='10' value=''> </br>
    <input type='text' name='periodTitle' placeholder='Заголовок' size='30' value=''>
    <textarea name='periodDescription' rows='5' cols='100'></textarea>
</c:if>
<c:if test="${param.type != 'EXPERIENCE' && param.type != 'EDUCATION'}">
    <textarea name='${param.type}' placeholder='${param.type} текст' rows='2' cols='40'></textarea>
</c:if>
<div style="margin-bottom: 30px;"></div>
