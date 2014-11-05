<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>Contract</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            Contract info:
        </p>
        <br>
        <p>
            Client: ${client.email}
        </p>
        <p>
            Balance: ${client.amount}
        </p>
        <p>
            Number: ${contract.number}
        </p>
        <p>
            Contract ID: ${contract.id}
        </p>
        <p>
            Tariff title: ${contract.tariff.title}
        </p>
        <p>
            Is blocked by operator: ${contract.isBlockedByOperator()}
        </p>
        <p>
            Is blocked by client: ${contract.isBlockedByClient()}
        </p>
        <c:if test="${contract.isBlockedByOperator() == false && contract.isBlockedByClient() == false && contract.getClient().amount > 0}">
            <br>
            <p>

            <form id="formId3" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value=${contract.id}>
                <input type="hidden" name="action" value="changeTariff">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                <a class="inline-link" href="#" onclick="document.getElementById('formId3').submit()">Change tariff or options</a>
            </form>

            </p>
        </c:if>
    </div>

    <div class="inner-wrapper">
        <p>
            List of connected options:
            <c:choose>
                <c:when test="${contract.getOptions().size() != 0}">
        </p>
            <br>
                    <table>
                        <tr>
                            <td>
                                Title
                            </td>
                            <td>
                                Price
                            </td>
                            <td>
                                Cost of connection
                            </td>
                        </tr>
                        <c:forEach var="dependentOption" items="${contract.getOptions()}">
                            <tr>
                                <td>
                                        ${dependentOption.title}
                                </td>
                                <td>
                                        ${dependentOption.price}
                                </td>
                                <td>
                                        ${dependentOption.costOfConnection}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    empty.
                    </p>
                </c:otherwise>
            </c:choose>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>

</div>

</body>
</html>
