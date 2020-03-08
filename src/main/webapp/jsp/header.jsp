<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/customTags.tld"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <div class="upper-header">
        <div class="language-contaier">
            <i class="fas fa-globe" style="color: white;
    font-size: 15pt;
    padding-top: 0.8vw;
    margin-right: 1vw;"></i>
                <form action="controller" method="get" class="local-form">
                    <input type="hidden" name="language" value="ru_RU">
                    <input id="testVal" class="lang-submit" type="submit" value="РУС">
                </form>
                <form action="controller" method="get" class="local-form">
                    <input type="hidden" name="language" value="en_US">
                    <input  class="lang-submit line" type="submit" value="ENG">
                </form>
        </div>
        <div class="role-con">
        <ctg:role role="${current_user.role}" />
        </div>
    </div>
    <div class="lower-header">
        <div class="logo">
            <img src="./img/logo.png" alt="" width="300">
        </div>
        <div class="nav-container">
            <form class="nav-form" action="controller" method="get">
                <input type="hidden" name="command" value="home_page">
                <input class="nav-button" type="submit" value="<fmt:message key="label.button.home" />">
            </form>
            <c:if test="${userRole == 'GUEST' || userRole == null}">
                <form class="nav-form" action="controller" method="post">
                    <input type="hidden" name="command" value="login_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.login" />">
                </form>
            </c:if>
            <c:if test="${userRole == 'CUSTOMER'}">
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="personal_customer_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.personal_page" />">
                </form>
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="make_appointment_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.make_appointment" />">
                </form>
            </c:if>
            <c:if test="${userRole == 'ADMIN'}">
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="doctors_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.doctors" />">
                </form>
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="departments_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.departments" />">
                </form>
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="add_department_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.create_department" />">
                </form>
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="add_doctor_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.create_doctor" />">
                </form>
            </c:if>
            <c:if test="${userRole == 'DOCTOR'}">
                <form class="nav-form" method="post" action="controller">
                    <input type="hidden" name="command" value="doctor_appointments_page">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.appointments" />">
                </form>
            </c:if>
            <c:if test="${userRole == 'ADMIN' || userRole == 'CUSTOMER' || userRole == 'DOCTOR'}">
                <form class="nav-form" action="controller" method="post">
                    <input type="hidden" name="command" value="logout">
                    <input class="nav-button" type="submit" value="<fmt:message key="label.button.logout" />">
                </form>
            </c:if>
        </div>
    </div>
</header>
</body>
</html>
