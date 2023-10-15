<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type='text' style="font-size: 16px;" name='${param.type}' size='30' value=''
       placeholder='Название Организации'> <br>
<input type='text' style="font-size: 16px;" name='${param.type}_webSite' size='50' value=''
       placeholder='Сайт Организации'> <br>

<input type='month' name='${param.type}_startDate${param.i}' size='10' value='' placeholder='Дата начала'>
<input type='month' name='${param.type}_endDate${param.i}' size='10' value='' placeholder='Дата конца'><br>
<input type='text' name='${param.type}_periodTitle${param.i}' size='100' value='' placeholder='Заголовок'><br>
<textarea name='${param.type}_periodDescription${param.i}' rows='5' cols='100' placeholder='Описание'></textarea><br>

<div style="margin-bottom: 30px;"></div>
