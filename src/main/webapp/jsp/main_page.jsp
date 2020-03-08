<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Home</title>
    <link href="<c:url value="/css/main_menu.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
</head>
<body>
<c:import url="/jsp/header.jsp" />
<article>

</article>
<footer class="footer-distributed">
    <div class="footer-left">
        <div class="logo logo2">
            <img src="./img/logo.png" alt="" width="300">
        </div>
        <p class="footer-links">
        </p>
        <p class="footer-company-name">Health Flex © 2015</p>
    </div>

    <div class="footer-center">
        <div>
            <i class="fa fa-map-marker"></i>
            <p><span><fmt:message key="label.text.street" /> </span><fmt:message key="label.text.city" /></p>
        </div>

        <div>
            <i class="fa fa-phone"></i>
            <p>+1 555 123456</p>
        </div>

        <div>
            <i class="fa fa-envelope"></i>
            <p><a href="mailto:support@company.com">support@HealthFlex.com</a></p>
        </div>
    </div>
    <div class="footer-right">
        <p class="footer-company-about">
            <span><fmt:message key="label.text.about" /> </span>
            <fmt:message key="label.text.about_text" />
        </p>
        <div class="footer-icons">
            <a href="#"><i class="fab fa-facebook-square"></i></a>
            <a href="#"><i class="fab fa-twitter-square"></i></a>
            <a href="#"><i class="fab fa-linkedin"></i></a>
            <a href="#"><i class="fab fa-github-square"></i></a>
        </div>
    </div>
</footer>
<script src="<c:url value="/js/main_page.js "/>">
</script>
<c:if test="${wrongAction != null}">
    <script>
        errorAlert("${wrongAction}","" ,"<fmt:message key="title.submit_button" />")
    </script>
</c:if>
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
