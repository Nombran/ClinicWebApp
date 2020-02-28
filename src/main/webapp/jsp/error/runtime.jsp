<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="property.pagecontent"/>
<%@page isErrorPage = "true" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Show Error Page</title>
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
    <a class='btn newbtn' href='controller?command=home_page'>BACK TO HOMEPAGE</a>
    <h1 class="newh1">AN ERROR OCCURRED</h1>
    <table width = "100%" border = "1">
        <tr valign = "top">
            <td width = "40%"><b>Error:</b></td>
            <td>${pageContext.exception}</td>
        </tr>

        <tr valign = "top">
            <td><b>URI:</b></td>
            <td>${pageContext.errorData.requestURI}</td>
        </tr>

        <tr valign = "top">
            <td><b>Status code:</b></td>
            <td>${pageContext.errorData.statusCode}</td>
        </tr>

        <tr valign = "top">
            <td><b>Stack trace:</b></td>
            <td>
                <c:forEach var = "trace"
                           items = "${pageContext.exception.stackTrace}">
                    <p>${trace}</p>
                </c:forEach>
            </td>
        </tr>
    </table>
</div>
</body>
</html>