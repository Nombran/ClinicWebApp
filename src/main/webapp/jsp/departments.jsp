<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.add_department_pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link rel="stylesheet" type="text/css"
          href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/departments.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <script src="<c:url value="/js/slick.min.js "/>"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<div class="result-container">
    <div class="choose-container">
        <div class="scroll-container">

        </div>
    </div>
</div>
<script src="<c:url value="/js/departments.js "/>">
</script>
<script>
    setLocale("${language}");
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}","Successfull","Submit");
    </script>
</c:if>
</body>
</html>