<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="property.pagecontent"/>
<%@ page isELIgnored="false" %>
<html>

<head>
    <title>Edit doctor</title>
    <link href="<c:url value="/css/add_doctor.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/js/notiflix-aio-1.9.1.min.js"/>"></script>
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="https://kit.fontawesome.com/9d248a417b.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css?family=Philosopher&display=swap&subset=cyrillic" rel="stylesheet">
</head>

<body>
<c:import url="/jsp/header.jsp" />
<article>
    <form action="controller" class="addDoctorForm" method="post" enctype="multipart/form-data">
        <div class="data-container">
            <div class="account-data">
                <h1><fmt:message key="label.text.account_data" /></h1>
                <input type="hidden" name="command" value="edit_doctor">
                <input type="text" name="login" pattern="[a-zA-Z][A-Za-z0-9]{7,15}" required title="<fmt:message key="title.login" />" placeholder=<fmt:message key="label.login"/> />
                <input type="password" name="password" pattern= "(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}"  required title="<fmt:message key="title.password" />" placeholder=<fmt:message key="label.password" /> />
                <input type="email" name="email" required title="<fmt:message key="title.email" />" placeholder="Email" />
            </div>
            <div class="img-container">
                <img id="dep-img" src="./img/default-avatar.png" alt="" width="200">
                <div class="input-file-container">
                    <input type="file" name="image"  class="input-file" id="my-file" >
                    <label tabindex="0" for="my-file" class="input-file-trigger"><fmt:message key="label.button.select_file" /></label>
                </div>
                <p class="file-return"></p>
            </div>
            <div class="doctor-data">
                <h1><fmt:message key="label.text.doctor_data" /></h1>
                <input type="text" name="name" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.name" /> />
                <input type="text" name="surname" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.surname" /> />
                <input type="text" name="lastname" pattern="[A-Za-zА-Яа-я]{2,20}" required title="<fmt:message key="title.name" />" placeholder=<fmt:message key="label.lastName" /> />
                <input type="text" required name="specialization" placeholder="Specialization" title="<fmt:message key="label.title.specialization" /> " pattern="[а-яёА-ЯЁ]{4,20}\s*[-а-яёА-ЯЁ]{1,20}">
                <input type="text" required name="category" placeholder="Category" pattern="[а-яёА-ЯЁ]{4,10}\s*[а-яёА-ЯЁ]{4,15}" title="<fmt:message key="label.title.category" /> ">
                <select name="department_id" id="department-select" class="department-choose">
                </select>
            </div>
        </div>
        <input type="submit" id="save-button" value="<fmt:message key="label.button.save" />">
    </form>
</article>
<script src="<c:url value="/js/edit_doctor.js "/>">
</script>
<c:if test="${result != null}">
    <script>
        initAlert("${result}", "<fmt:message key="${result}" />","<fmt:message key="title.submit_button" />")
    </script>
    <c:remove var="result" scope="session"/>
</c:if>
<c:if test="${doctor != null && user != null}">
    <script>
        initInputs(${doctor},${user});
    </script>
</c:if>
</body>

</html>
