<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type='month' name='${param.type}_startDate${param.i}${param.k}' size='10' value='' placeholder='Дата начала'><br>
<input type='month' name='${param.type}_endDate${param.i}${param.k}' size='10' value='' placeholder='Дата конца'><br>
<input type='text' name='${param.type}_periodTitle${param.i}${param.k}' size='100' value='' placeholder='Заголовок'><br>
<textarea name='${param.type}_periodDescription${param.i}${param.k}' rows='5' cols='100' placeholder='Описание'></textarea><br>

<div style="margin-bottom: 30px;"></div>
