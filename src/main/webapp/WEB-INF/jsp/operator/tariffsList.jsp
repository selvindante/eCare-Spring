<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>List of all tariffs</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

            <form id="formId3" method="post" action="newTariff" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                Tariffs list: <a class="inline-link-add" title="Create new tariff" href="#" onclick="document.getElementById('formId3').submit()"></a>
            </form>

            <c:choose>
                <c:when test="${tariffs.size() != 0}">

                    </p>
                    <br>
                    <table>
                        <tr>
                            <th>
                                Tariff ID
                            </th>
                            <th>
                                Title
                            </th>
                            <th>
                                Price
                            </th>
                            <th style="width: 0">
                                    ${HtmlUtil.EMPTY_TD}
                            </th>
                        </tr>
                        <c:forEach var="tariff" items="${tariffs}">
                            <tr>
                                <td>

                                    <form id="formId5${tariff.id}" method="post" action="viewTariff" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${tariff.id}>
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link" href="#" onclick="document.getElementById('formId5${tariff.id}').submit()">${tariff.id}</a>
                                    </form>

                                </td>
                                <td>
                                        ${tariff.title}
                                </td>
                                <td>
                                        ${tariff.price}
                                </td>
                                <td style="width: 0">

                                    <form id="formId6${tariff.id}" method="post" action="deleteTariff" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${tariff.id}>
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link-delete" title="Delete tariff" href="#" onclick="document.getElementById('formId6${tariff.id}').submit()"></a>
                                    </form>

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
