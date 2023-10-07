<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="ru.javawebinar.basejava.model.ContactType" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.Section" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.model.Company" %>
<%@ page import="ru.javawebinar.basejava.model.Company.Period" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlMapper" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8" pageEncoding="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
          <dl>
              <dt>Имя:</dt>
              <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
          </dl>
          <h3>Контакты</h3>
          <p>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                  <dt>${type.title}</dt>
                  <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                </dl>
                </c:forEach>
          </p>
          <h3>Секции:</h3>
          <p>
              <c:forEach var="type" items="${SectionType.values()}">
                  <c:choose>
                      <c:when test="${resume.getSection(type) == null}">
                          <c:import url="empty_section_template.jsp">
                              <c:param name="type" value="${type}" />
                          </c:import>
                      </c:when>
                      <c:otherwise>
                          <c:if test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                              <dl>
                                  <dt>${type.title}</dt>
                                  <dd>
                                      <textarea name="${type}" rows="4" cols="50">${resume.getSection(type).getText()}</textarea>
                                  </dd>
                              </dl>
                          </c:if>
                          <c:if test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                               <dl>
                                   <dt>${type.title}</dt>
                                   <dd>
                                       <textarea name="${type}" rows="10" cols="100" placeholder="Для маркированного текста используйте enter">${section}</textarea>
                                   </dd>
                               </dl>
                          </c:if>
                          <c:if test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                              <c:set var="companySection" value="${resume.getSection(type)}"/>
                              <c:forEach var="company" items="${companySection.companies}">
                                  <!-- Формы для каждой компании в секции -->
                                  <input type='text' name='CompanyName' size='30' value='${company.companyName}' placeholder='Название Организации'> </br>

                                  <input type='text' name='WebSite' size='30' value='${company.getWebsite()}' placeholder='Сайт Организации'> </br>

                                  <c:forEach var="period" items="${company.getPeriods()}">
                                      <!-- Формы для каждого периода в компании -->
                                      <input type='text' name='startDate' size='10' value='${period.getStartDate()}' placeholder='Дата начала'>
                                      <input type='text' name='endDate' size='10' value='${period.getEndDate()}' placeholder='Дата конца'></br>
                                      <input type='text' name='periodTitle' size='30' value='${period.getTitle()}' placeholder='Заголовок'></br>
                                      <textarea name='periodDescription' rows='5' cols='100' placeholder='Описание'>${period.getDescription()}</textarea></br>
                                  </c:forEach>
                                  <div style="margin-bottom: 30px;"></div>
                              </c:forEach>
                          </c:if>
                      </c:otherwise>
                  </c:choose>
              </c:forEach>
          </p>
          <hr>
          <button type="submit">Сохранить</button>
          <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
