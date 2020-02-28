<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Doctors</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link rel="stylesheet" type="text/css"
          href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/doctors.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <script src="<c:url value="/js/slick.min.js "/>"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<div class="descr-container">
</div>
<div class="stage-container">
    <div class="doc-choosed" style="display: none">
        <div class="choosed-doc-img"></div>
        <i class="far fa-times-circle doc-close"></i>
    </div>
</div>
<div class="result-container">
    <div class="choose-container">
        <div class="scroll-container">

        </div>
    </div>
</div>
<script src="<c:url value="/js/doctors.js "/>" type="text/javascript" charset="utf-8">
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
