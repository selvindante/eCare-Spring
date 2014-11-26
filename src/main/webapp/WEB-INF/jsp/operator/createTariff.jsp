<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Creating of tariff</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <form:form method="post" action="createTariff" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="sessionRole" value=${role}>
            <p>
                Title:
                <br>
                <input type="text" placeholder="title"  class="simple-input" name="title" size=20 value=""> *
            </p>
            <br>
            <p>
                Price:
                <br>
                <input type="text" placeholder="price"  class="simple-input" name="price" size=20 value=""> *
            </p>
            <br>
            <p>
                (*) - required fields.
            </p>
            <br>
            <button type="submit" class="modern">Create</button>
            <a class="inline-link" href="#" onclick="document.getElementById('formId3').submit()">Back</a>
        </form:form>

        <form id="formId3" method="post" action="viewAllTariffs" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="sessionRole" value=${role}>
        </form>

    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
