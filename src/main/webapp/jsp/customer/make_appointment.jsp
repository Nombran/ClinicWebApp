<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Make appointment</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link rel="stylesheet" type="text/css"
          href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/make_appointment.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <script src="<c:url value="/js/slick.min.js "/>"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<div class="descr-container">
    <h1></h1>
</div>
<div class="stage-container">
    <div class="dep-choosed" style="display: none">
        <div class="choosed-dep-img"></div>
        <i class="far fa-times-circle dep-close"></i>
    </div>
    <div class="doc-choosed" style="display: none">
        <div class="choosed-doc-img"></div>
        <i class="far fa-times-circle doc-close"></i>
    </div>
    <form id="purpose-input-form">
    <input id="purpose-input" required type="text" name="purpose" placeholder="<fmt:message key="label.placeholder.purpose" /> ">
    </form>
</div>
<div class="result-container">
    <div class="choose-container">
        <h4 class="no-doc-apps" style="display: none"><fmt:message key = "label.no_doctor_apps" /></h4>
        <div class="scroll-container">
        </div>
    </div>
</div>
<script src="<c:url value="/js/make_appointment.js "/>">
</script>
<script>
    setLocale("${language}");
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}", "<fmt:message key="${result}" />","<fmt:message key="title.submit_button" />")
    </script>
    <c:remove var="result" scope="session"/>
</c:if>
</body>
</html>
