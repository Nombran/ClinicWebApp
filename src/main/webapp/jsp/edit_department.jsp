<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>

<head>
    <title>Title</title>
    <link href="<c:url value="/css/add_department.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
</head>

<body>
<c:import url="/jsp/header.jsp" />
<div class="form-container">
    <form action="controller" class="addDepartmentForm" method="post" enctype="multipart/form-data">
        <div class="second-con">
            <div class="content-con">
                <h1><fmt:message key="label.text.edit_dep" /></h1>
                <input type="hidden" name="command" value="edit_department">
                <input type="text" name="name" required placeholder="<fmt:message key="label.placeholder.dep_name" /> " pattern="[-а-яёА-ЯЁ]{4,30}\s*[а-яёА-ЯЁ]{4,30}" title="<fmt:message key = "label.title.dep_name" />" >
                <textarea name="description" minlength="30" maxlength="500" rows="15" placeholder="<fmt:message key="label.placeholder.dep_descrition" />"></textarea>
                <input type="text" name="phone" required pattern="[\+]\d{12}" title="<fmt:message key="title.phone" />" placeholder="<fmt:message key="label.placeholder.dep_phone" /> ">
            </div>
            <div class = "image-content">
                <div class="input-file-container">
                    <img id="dep-img" src="./img/default-avatar.png" alt="" width="200">
                    <input class="input-file" id="my-file" type="file" name="image">
                    <label tabindex="0" for="my-file" class="input-file-trigger"><fmt:message key="label.button.select_file" /></label>
                </div>
                <p class="file-return"></p>
            </div>
        </div>
        <input class="submit-class" type="submit" value="<fmt:message key="label.button.save" />">
    </form>
</div>
<script src="<c:url value="/js/edit_department.js "/>">
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}", "<fmt:message key="${result}" />","<fmt:message key="title.submit_button" />")
    </script>
</c:if>
<c:if test="${department != null}" >
    <script>
        initInputs(${department});
    </script>
</c:if>
</body>
</html>