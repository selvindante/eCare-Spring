<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>New contract</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            Creating of new contract for client: ${client.getFullName()}
        </p>
        <br>

        <form:form id="formId2" method="post" action="createContract" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value="${client.id}">
            <input type="hidden" name="sessionRole" value=${role}>
            <p>
                Telephone number (must be unique): <input type="text" placeholder="telephone number" class="simple-input" name="number" size=25 value="">
            </p>
            <button type="submit" class="modern">Create</button>
            <a class="inline-link" href="#" onclick="document.getElementById('h3ClientForm').submit()">Back</a>
        </form:form>

    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
