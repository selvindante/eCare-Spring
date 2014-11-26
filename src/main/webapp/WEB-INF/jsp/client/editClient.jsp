<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Edit client info</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <form:form method="post" action="updateClient" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value=${client.id}>
            <input type="hidden" name="sessionRole" value=${role}>
            <p>
                Name*:
                <br>
                <input type="text" class="simple-input" name="name" size=25 value="${client.name}">
                <span style="font-size: 12px; color: lightgray">Example: John</span>
            </p>
            <br>
            <p>
                Lastname:
                <br>
                <input type="text" class="simple-input" name="lastname" size=25 value="${client.lastname}">
                <span style="font-size: 12px; color: lightgray">Example: Johnson</span>
            </p>
            <br>
            <p>
                Birth date*:
                <br>
                <input type="text" class="simple-input" name="birthdate" size=25 value="${client.getBirthDateToString()}">
                <span style="font-size: 12px; color: lightgray">Example: 1900-01-01</span>
            </p>
            <br>
            <p>
                Passport*:
                <br>
                <input type="text" class="simple-input"  name="passport" size=25 value="${client.passport}">
                <span style="font-size: 12px; color: lightgray">Example: 1234567890</span>
            </p>
            <br>
            <p>
                Address:
                <br>
                <input type="text" class="simple-input" name="address" size=25 value="${client.address}">
                <span style="font-size: 12px; color: lightgray">Example: London, Baker Street, 221B</span>
            </p>
            <br>
            <p>
                (*) - required fields.
            </p>
            <br>
            <button type="submit" class="modern">Save</button>
        </form:form>
    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
