<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>

<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./resources/css/style.css">
    <title>Login</title>
</head>

<body  onload='document.loginForm.username.focus();'>

<div class="outer-wrapper clearfix">

    <h3>
        <div class="h3-logo"></div>
    </h3>

    <c:if test="${errormessage != null}">
        <div class="inner-wrapper-error" id="error">
            <p>
                Error: ${errormessage}
            </p>
        </div>

        <SCRIPT language="javascript">
            setInterval(function() {
                $("#error").attr("hidden", true);
            }, 5000);
        </SCRIPT>

    </c:if>

    <c:if test="${successmessage != null}">
        <div class="inner-wrapper-success" id="success">
            <p>
                Success: ${successmessage}
            </p>
        </div>

        <SCRIPT language="javascript">
            setInterval(function() {
                $("#success").attr("hidden", true);
            }, 5000);
        </SCRIPT>

    </c:if>

    <div class="inner-wrapper" style="padding-left: 100px">

        <form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
            <p>
                Login:
                &emsp;
                &emsp;
                <input type="text" placeholder="login" class="simple-input" name="username" size=20 value="">
            </p>
            <br>
            <p>
                Password:
                &nbsp;
                <input type="password" placeholder="password" class="simple-input" name="password" size=20 value="">
            </p>
            <br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit" class="modern">Enter</button>
            <a href="#" onclick="document.getElementById('formId1').submit()" class="inline-link">Registration</a>
        </form>

        <form:form id="formId1" method="post" action="registration" enctype="application/x-www-form-urlencoded">
        </form:form>

    </div>

    <jsp:include page="fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
