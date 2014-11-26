<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ru.tsystems.tsproject.ecare.entities.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Client page</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <header>
            Personal info of ${client.getFullName()}: <a href="#" onclick="document.getElementById('formId3').submit()" class="inline-link-edit" title="Edit client personal info"></a>
        </header>

        <form:form id="formId3" method="post" action="editClient" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value=${client.id}>
            <input type="hidden" name="sessionRole" value=${role}>
        </form:form>

        <br>
        <p>
            Name: ${client.name}
        </p>
        <p>
            Lastname: ${client.lastname}
        </p>
        <p>
            Birthdate: ${client.getBirthDateToString()}
        </p>
        <p>
            E-mail: ${client.email}
        </p>
        <p>
            Address: ${client.address}
        </p>
        <p>
            Passport: ${client.passport}
        </p>
        <p>
            Balance: ${client.amount}
        </p>
        <form:form method="post" action="addAmount" enctype="application/x-www-form-urlencoded">
            <p>
                <input type="hidden" name="id" value=${client.id}>
                <input type="hidden" name="sessionRole" value=${role}>
                <input type="text" placeholder="amount" class="simple-input" name="amount" size=10 value="">
                <button type="submit" class="modern">Add amount</button>
            </p>
        </form:form>
    </div>

    <div class="inner-wrapper">
            <form:form id="formId4" method="post" action="newContract" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value="${client.id}">
                <input type="hidden" name="sessionRole" value=${role}>
            </form:form>
        <p>
            Contracts list:
        <c:choose>
            <c:when test="${client.getContracts().size() != 0}">
                <c:if test="${role == 'ROLE_ADMIN'}">
                    <a class="inline-link-add" title="Add new contract" href="#" onclick="document.getElementById('formId4').submit()"></a>
                </c:if>
            </p>
            <br>
                <table>
                    <tr>
                        <th>
                            Number
                        </th>
                        <th>
                            Tariff
                        </th>
                        <th>
                            Blocked by client
                        </th>
                        <th style="width: 170px">
                            Blocked by operator
                        </th>
                        <c:if test="${role == 'ROLE_ADMIN'}">
                            <th style="width: 0">
                                    ${HtmlUtil.EMPTY_TD}
                            </th>
                        </c:if>
                        <th style="width: 0">
                                ${HtmlUtil.EMPTY_TD}
                        </th>
                    </tr>
                    <c:forEach var="contract" items="${client.getContracts()}">
                        <c:choose>
                            <c:when test="${contract.isBlockedByClient() || contract.isBlockedByOperator()}">
                                <tr style="background-color: rgba(255, 232, 232, 1);">
                            </c:when>
                            <c:otherwise>
                                <tr>
                            </c:otherwise>
                        </c:choose>
                            <td>

                                <form:form id="formId1${contract.id}" method="post" action="viewContract" enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="id" value=${contract.id}>
                                    <input type="hidden" name="sessionRole" value=${role}>
                                    <a class="inline-link" href="#" onclick="document.getElementById('formId1${contract.id}').submit()">${contract.number}</a>
                                </form:form>

                            </td>
                            <td>
                                ${contract.tariff.title}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${contract.isBlockedByClient()}">Yes</c:when>
                                    <c:otherwise>No</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${contract.isBlockedByOperator()}">Yes</c:when>
                                    <c:otherwise>No</c:otherwise>
                                </c:choose>
                            </td>
                            <td style="width: 0">
                                <c:choose>
                                    <c:when test="${role == 'ROLE_ADMIN'}">

                                        <c:choose>
                                            <c:when test="${contract.isBlockedByOperator() == true}">

                                                <form:form id="formId3${contract.id}" method="post" action="unblockByOperator" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="sessionRole" value=${role}>
                                                    <a class="inline-link-unlock" title="Unblock contract" href="#" onclick="document.getElementById('formId3${contract.id}').submit()"></a>
                                                </form:form>

                                            </c:when>
                                            <c:otherwise>

                                                <form:form id="formId4${contract.id}" method="post" action="blockByOperator" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="sessionRole" value=${role}>
                                                    <a class="inline-link-lock" title="Block contract" href="#" onclick="document.getElementById('formId4${contract.id}').submit()"></a>
                                                </form:form>

                                            </c:otherwise>
                                        </c:choose>

                                        </td>

                                        <td style="width: 0">

                                            <form:form id="formId2${contract.id}" method="post" action="deleteContract" enctype="application/x-www-form-urlencoded">
                                                <input type="hidden" name="id" value=${contract.id}>
                                                <input type="hidden" name="sessionRole" value=${role}>
                                                <a class="inline-link-delete" title="Delete contract" href="#" onclick="document.getElementById('formId2${contract.id}').submit()"></a>
                                            </form:form>

                                    </c:when>
                                    <c:when test="${role == 'ROLE_USER' && contract.isBlockedByOperator() == false}">
                                        <c:choose>
                                            <c:when test="${contract.isBlockedByClient() == true}">

                                                <form:form id="formId5${contract.id}" method="post" action="unblockByClient" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="sessionRole" value=${role}>
                                                    <a class="inline-link-unlock" title="Unblock contract" href="#" onclick="document.getElementById('formId5${contract.id}').submit()"></a>
                                                </form:form>

                                            </c:when>
                                            <c:otherwise>

                                                <form:form id="formId6${contract.id}" method="post" action="blockByClient" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="sessionRole" value=${role}>
                                                    <a class="inline-link-lock" title="Block contract" href="#" onclick="document.getElementById('formId6${contract.id}').submit()"></a>
                                                </form:form>

                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                 empty.
                    <c:if test="${role == 'ROLE_ADMIN'}">
                        <a class="inline-link-add" title="Add new contract" href="#" onclick="document.getElementById('formId4').submit()"></a>
                    </c:if>
                </p>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
