<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ru.tsystems.tsproject.ecare.entities.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>Client page</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            Personal info: <a href="#" onclick="document.getElementById('formId3').submit()" class="inline-link-edit" title="Edit client personal info"></a>
        </p>

        <form id="formId3" method="post" action="client" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value=${client.id}>
            <input type="hidden" name="action" value="editClient">
            <input type="hidden" name="sessionRole" value=${session.role}>
            <input type="hidden" name="sessionStatus" value=${session.isOn()}>
        </form>

        <br>
        <p>
            ID: ${client.id}
        </p>
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
        <br>
        <form method="post" action="client" enctype="application/x-www-form-urlencoded">
            <p>
                <input type="hidden" name="id" value=${client.id}>
                <input type="hidden" name="action" value="addAmount">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                <input type="text" placeholder="amount" class="simple-input" name="amount" size=10 value="">
                <button type="submit" class="modern">Add amount</button>
            </p>
        </form>
    </div>

    <div class="inner-wrapper">
        <c:if test="${session.role == 'admin'}">
            <p>
            <form id="formId4" method="post" action="client" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value="${client.id}">
                <input type="hidden" name="action" value="createContract">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                <a class="inline-link" href="#" onclick="document.getElementById('formId4').submit()">Add new contract</a>
            </form>
            </p>
            <br>
        </c:if>
        <p>
            List of contracts:
        <c:choose>
            <c:when test="${client.getContracts().size() != 0}">
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
                            Is blocked by client?
                        </th>
                        <th>
                            Is blocked by operator?
                        </th>
                        <c:if test="${session.role == 'admin'}">
                            <th style="width: 0">
                                    ${HtmlUtil.EMPTY_TD}
                            </th>
                        </c:if>
                        <th style="width: 0">
                                ${HtmlUtil.EMPTY_TD}
                        </th>
                    </tr>
                    <c:forEach var="contract" items="${client.getContracts()}">
                        <tr>
                            <td>

                                <form id="formId1${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="id" value=${contract.id}>
                                    <input type="hidden" name="action" value="viewContract">
                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                    <a class="inline-link" href="#" onclick="document.getElementById('formId1${contract.id}').submit()">${contract.number}</a>
                                </form>

                            </td>
                            <td>
                                ${contract.tariff.title}
                            </td>
                            <td>
                                ${contract.isBlockedByClient()}
                            </td>
                            <td>
                                ${contract.isBlockedByOperator()}
                            </td>
                            <td style="width: 0">
                                <c:choose>
                                    <c:when test="${session.role == 'admin'}">

                                        <c:choose>
                                            <c:when test="${contract.isBlockedByOperator() == true}">

                                                <form id="formId3${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="action" value="unblockByOperator">
                                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                                    <a class="inline-link-unlock" title="Unblock contract" href="#" onclick="document.getElementById('formId3${contract.id}').submit()"></a>
                                                </form>

                                            </c:when>
                                            <c:otherwise>

                                                <form id="formId4${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="action" value="blockByOperator">
                                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                                    <a class="inline-link-lock" title="Block contract" href="#" onclick="document.getElementById('formId4${contract.id}').submit()"></a>
                                                </form>

                                            </c:otherwise>
                                        </c:choose>

                                        </td>

                                        <td style="width: 0">

                                            <form id="formId2${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                                <input type="hidden" name="id" value=${contract.id}>
                                                <input type="hidden" name="action" value="deleteContract">
                                                <input type="hidden" name="sessionRole" value=${session.role}>
                                                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                                <a class="inline-link-delete" title="Delete contract" href="#" onclick="document.getElementById('formId2${contract.id}').submit()"></a>
                                            </form>

                                    </c:when>
                                    <c:when test="${session.role == 'client' && contract.isBlockedByOperator() == false}">
                                        <c:choose>
                                            <c:when test="${contract.isBlockedByClient() == true}">

                                                <form id="formId5${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="action" value="unblockByClient">
                                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                                    <a class="inline-link-unlock" title="Unblock contract" href="#" onclick="document.getElementById('formId5${contract.id}').submit()"></a>
                                                </form>

                                            </c:when>
                                            <c:otherwise>

                                                <form id="formId6${contract.id}" method="post" action="contract" enctype="application/x-www-form-urlencoded">
                                                    <input type="hidden" name="id" value=${contract.id}>
                                                    <input type="hidden" name="action" value="blockByClient">
                                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                                    <a class="inline-link-lock" title="Block contract" href="#" onclick="document.getElementById('formId6${contract.id}').submit()"></a>
                                                </form>

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
                </p>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>

</div>

</body>
</html>
