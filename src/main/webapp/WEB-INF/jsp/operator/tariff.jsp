<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Tariff</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            Tariff ID: ${tariff.id}
        </p>
        <p>
            Title: ${tariff.title}
        </p>
        <p>
            Price: ${tariff.price}
        </p>

    </div>

    <div class="inner-wrapper">

            <form id="formId4" method="post" action="newOption" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value="${tariff.id}">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
            </form>

        Available options list:

            <c:choose>
                <c:when test="${tariff.getOptions().size() != 0}">
                    <a class="inline-link-add" title="Create new option" href="#" onclick="document.getElementById('formId4').submit()"></a>
                    </p>
                    <br>
                    <table>
                        <tr>
                            <th>
                                Option ID
                            </th>
                            <th>
                                Title
                            </th>
                            <th>
                                Price
                            </th>
                            <th>
                                Cost of connection
                            </th>
                            <th style="width: 0">
                                    ${HtmlUtil.EMPTY_TD}
                            </th>
                        </tr>
                        <c:forEach var="option" items="${tariff.getOptions()}">
                            <tr>
                                <td>

                                    <form id="formId6${option.id}" method="post" action="viewOption" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${option.id}>
                                        <input type="hidden" name="tariffId" value=${tariff.id}>
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link" href="#" onclick="document.getElementById('formId6${option.id}').submit()">${option.id}</a>
                                    </form>

                                </td>
                                <td>
                                        ${option.title}
                                </td>
                                <td>
                                        ${option.price}
                                </td>
                                <td>
                                        ${option.costOfConnection}
                                </td>
                                <td style="width: 0">

                                    <form id="formId7${option.id}" method="post" action="deleteOption" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${option.id}>
                                        <input type="hidden" name="tariffId" value=${tariff.id}>
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link-delete" title="Delete option" href="#" onclick="document.getElementById('formId7${option.id}').submit()"></a>
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    empty. <a class="inline-link-add" title="Create new option" href="#" onclick="document.getElementById('formId4').submit()"></a>
                </c:otherwise>
            </c:choose>

    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
