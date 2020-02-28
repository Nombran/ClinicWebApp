<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link href="<c:url value="/css/404.css"/>" rel="stylesheet">
</head>
<body>
<div id="clouds">
    <div class="cloud x1"></div>
    <div class="cloud x1_5"></div>
    <div class="cloud x2"></div>
    <div class="cloud x3"></div>
    <div class="cloud x4"></div>
    <div class="cloud x5"></div>
</div>
<div class='c'>
    <div class='_404'>404</div>
    <hr>
    <div class='_1'><fmt:message key="label.the_page" /> </div>
    <div class='_2'><fmt:message key="label.was_not_found" /> </div>
    <a class='btn' href='controller?command=home_page'><fmt:message key="label.backtohome" /></a>
</div>
</body>
</html>
