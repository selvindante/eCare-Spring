<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Contract</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <table style="font-weight: 700">
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Client:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${client.getFullName()}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Balance:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${client.amount}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Contract phone number:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${contract.number}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Tariff title:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    ${contract.tariff.title}
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Is blocked by operator:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    <c:choose>
                        <c:when test="${contract.isBlockedByOperator()}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td style="padding: 10px 10px 10px 20px; width: 250">
                    Is blocked by client:
                </td>
                <td style="width: 700; padding: 10px 10px 10px 20px">
                    <c:choose>
                        <c:when test="${contract.isBlockedByClient()}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>

        <%--<p>
            Client: ${client.getFullName()}
        </p>
        <p>
            Balance: ${client.amount}
        </p>
        <p>
            Contract phone number: ${contract.number}
        </p>
        <p>
            Tariff title: ${contract.tariff.title}
        </p>
        <p>
            Is blocked by operator:
            <c:choose>
                <c:when test="${contract.isBlockedByOperator()}">Yes</c:when>
                <c:otherwise>No</c:otherwise>
            </c:choose>
        </p>
        <p>
            Is blocked by client:
            <c:choose>
                <c:when test="${contract.isBlockedByClient()}">Yes</c:when>
                <c:otherwise>No</c:otherwise>
            </c:choose>
        </p>--%>
            <form:form id="formId3" method="post" action="changeTariff" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value=${contract.id}>
                <input type="hidden" name="sessionRole" value=${role}>
            </form:form>
    </div>

    <div class="inner-wrapper">
        <p>
            Connected options list:
            <c:choose>
                <c:when test="${contract.getOptions().size() != 0}">
                    <c:if test="${contract.isBlockedByOperator() == false && contract.isBlockedByClient() == false && contract.getClient().amount > 0}">
                        <a class="inline-link-edit" title="Change tariff or options" href="#" onclick="document.getElementById('formId3').submit()"></a>
                    </c:if>
        </p>
            <br>
                    <table>
                        <tr>
                            <th>
                                Title
                            </th>
                            <th>
                                Price
                            </th>
                            <th>
                                Cost of connection
                            </th>
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
                        <c:if test="${contract.isBlockedByOperator() == false && contract.isBlockedByClient() == false && contract.getClient().amount > 0}">
                            <a class="inline-link-edit" title="Change tariff or options" href="#" onclick="document.getElementById('formId3').submit()"></a>
                        </c:if>
                    </p>
                </c:otherwise>
            </c:choose>
    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
