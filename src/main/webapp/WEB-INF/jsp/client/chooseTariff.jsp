<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Step 1. Choose tariff</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper-basket">

        <header>
            Basket
        </header>
        <p>
            Contract phone number: ${contract.number}
        </p>
        <p>
            Client balance: ${contract.getClient().amount}
        </p>
    </div>

    <div class="inner-wrapper">
        <p>
            Step 1. Choose new tariff.
        </p>
        <br>
        <p>
            Available tariffs list:
                <c:choose>
                    <c:when test="${tariffs.size() != 0}">
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
                            </tr>
                            <c:forEach var="tariff" items="${tariffs}">
                                <tr>
                                    <td>
                                        <form:form id="formId3${tariff.id}" method="post" action="chooseTariff" enctype="application/x-www-form-urlencoded">
                                            <input type="hidden" name="contractId" value=${contract.id}>
                                            <input type="hidden" name="tariffId" value=${tariff.id}>
                                            <input type="hidden" name="sessionRole" value=${role}>
                                            <a class="inline-link" href="#" onclick="document.getElementById('formId3${tariff.id}').submit()">${tariff.title}</a>
                                        </form:form>
                                    </td>
                                    <td>
                                        ${tariff.price}
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

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
