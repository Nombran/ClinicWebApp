<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <title>Login</title>
</head>
<body>
<div class="home-button-con">
    <form class="nav-form" action="controller" method="get">
        <input type="hidden" name="command" value="home_page">
        <input class="nav-button" type="submit" value="<fmt:message key="label.button.home" />">
    </form>
</div>
<div class="container" id="container">
    <div class="form-container sign-up-container">
        <form action="controller" method="post"  id="newAccountForm">
            <h2><fmt:message key="label.createAccount"/></h2>
            <input type="hidden" name="command" value="registration"/>
            <input type="text" name="login" pattern="[a-zA-Z][A-Za-z0-9]{7,15}" required title="<fmt:message key="title.login" />" placeholder=<fmt:message key="label.login"/> />
            <input type="password" name="password" pattern= "(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}"  required title="<fmt:message key="title.password" />" placeholder=<fmt:message key="label.password"  /> />
            <input type="text" name="name" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.name" /> />
            <input type="text" name="surname" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.surname" /> />
            <input type="text" name="lastname" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.lastName" /> />
            <input type="date" name="birthday" min="1930-01-01" max="2010-01-01" required title="<fmt:message key="title.birthday" />" placeholder=<fmt:message key="label.birthday" /> />
            <input type="email" name="email" required title="<fmt:message key="title.email" />" placeholder="Email" />
            <input type="text" name="phone" required pattern="[\+]\d{12}" title="<fmt:message key="title.phone" />" placeholder=<fmt:message key="label.phone" /> />
            <input type="submit" value="<fmt:message key="label.signupButton"/>" />
        </form>
    </div>
    <div class="form-container sign-in-container">
        <form action="controller" method="post" name="signInForm">
            <input type="hidden" name="command" value="login"/>
            <h1><fmt:message key="label.signIn" /></h1>
            <input type="text" name="login" title="<fmt:message key="title.login" />" pattern="[a-zA-Z][A-Za-z0-9]{7,15}" required placeholder=<fmt:message key="label.login" /> />
            <input type="password" name="password" pattern= "(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}"  required title="<fmt:message key="title.password" />" placeholder=<fmt:message key="label.password" /> />
            <a href="#"></a>
            <button><fmt:message key="label.signIn" /></button>
        </form>
    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                <h1><fmt:message key="label.welome" /></h1>
                <p><fmt:message key="label.registration_description" /></p>
                <button class="ghost" id="signIn"><fmt:message key="label.signIn" /></button>
            </div>
            <div class="overlay-panel overlay-right">
                <h1><fmt:message key="label.welcomeBack" /></h1>
                <p><fmt:message key="label.singIn_Description" /></p>
                <button class="ghost" id="signUp"><fmt:message key="label.signupButton" /></button>
            </div>
        </div>
    </div>
</div>

<footer>
    <div class="language-contaier">
        <i class="fas fa-globe"></i>
        <form action="controller" method="get" class="local-form">
            <input type="hidden" name="language" value="ru_RU">
            <input id="testVal" class="lang-submit" type="submit" value="РУС">
        </form>
        <form action="controller" method="get" class="local-form">
            <input type="hidden" name="language" value="en_US">
            <input id="testVal2" class="lang-submit line" type="submit" value="ENG">
        </form>
    </div>
</footer>
<script src="<c:url value="/js/login.js"/>">
</script>
<c:if test="${registration_result != null}">
    <script>
        resultAlert("<fmt:message key="message.successful_registration" />","<fmt:message key="message.failed_registration" />","${registration_result}","<fmt:message key="${registration_result}" />","<fmt:message key="message.continue" />", "<fmt:message key="title.submit_button" />");
    </script>
</c:if>
<c:if test="${result != null}">
    <script>
        initAlert("${result}", "<fmt:message key="${result}" />","<fmt:message key="title.submit_button" />")
    </script>
    <c:remove var="result" scope="session" />
</c:if>
</body>
</html>