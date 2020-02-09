<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/personal_page.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<form class="schedule-container">
</form>
<h1 class="free-h1"><fmt:message key="label.text.my_tickets" /></h1>
<div class="active-appointments">
    <c:forEach var="appointment" items="${appointments}">
    <div class="active-appointment">
        <div class="full-date"><c:out value="${appointment.dateTime.toLocalDate()} ${appointment.dateTime.toLocalTime()}" /></div>
        <c:forEach var="doctor" items="${doctors}">
            <c:if test="${doctor.id == appointment.doctorId}">
                <div class="doc-name"><c:out value="${doctor.surname} ${doctor.name} ${doctor.lastname}" /></div>
                <div class="doc-specialization">${doctor.specialization}</div>
                <div class="purpose">${appointment.purpose}</div>
                <form method="post" action="controller">
                    <input type="hidden" name="id" value="${appointment.id}">
                    <input type="hidden" name="command" value="delete_ticket_reservation">
                    <input type="submit" value="<fmt:message key="label.button.cancel_ticket" /> ">
                </form>
            </c:if>
        </c:forEach>
    </div>
    </c:forEach>
</div>
<script src="<c:url value="/js/personal_page.js "/>">
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}","Successfull","Submit");
    </script>
</c:if>
</body>
</html>
