<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link rel="stylesheet" type="text/css"
          href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
    <link href="<c:url value="/css/doctor_appointments.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <script src="<c:url value="/js/slick.min.js "/>"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<form class="schedule-container" action="controller" method="post">
    <div class="new-app">
        <span class="new-app-text"><fmt:message key="label.text.new_appointmet" /></span>
        <input type="hidden" name="command" value="add_appointment">
        <input type="datetime-local" name="date_time" id="datetime">
        <input type="submit" value="<fmt:message key="label.button.create_ticket" />" id="create-button">
    </div>
</form>
<h1 class="free-h1"><fmt:message key="label.text.free_apps" /></h1>
<div class="free-container">
    <div class="scroll-free">
    </div>
</div>
<h1 class="busy-h1"><fmt:message key="label.text.busy_tickets" /> </h1>
<div class="busy-container">
    <div class="scroll-busy">
    </div>
</div>
<script src="<c:url value="/js/doctor_appointments.js "/>">
</script>
<script>
    setLocale("${language}");
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}", "<fmt:message key="${result}" />","<fmt:message key="title.submit_button" />")
    </script>
</c:if>
<c:if test="${doctors_appointments != null}" >
    <script>
        initFreeBlock(${doctors_appointments});
    </script>
</c:if>
<c:if test="${doctors_future_appointments != null}" >
    <script>
            initActivetickets(${doctors_future_appointments}, ${doctors_customers});
    </script>
</c:if>
</body>
</html>
