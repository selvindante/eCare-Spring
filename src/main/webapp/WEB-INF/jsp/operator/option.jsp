<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Option</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="../fragments/header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            Tariff ID: ${tariff.id}
        </p>
        <p>
            Tariff title: ${tariff.title}
        </p>
        <br>
        <p>
            Option ID: ${option.id}
        </p>
        <p>
            Option title: ${option.title}
        </p>
        <p>
            Option  price: ${option.price}
        </p>
        <p>
            Cost of connection: ${option.costOfConnection}
        </p>
        <form id="formId4" method="post" action="editOption" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value="${option.id}">
            <input type="hidden" name="tariffId" value="${tariff.id}">
            <input type="hidden" name="sessionRole" value=${session.role}>
            <input type="hidden" name="sessionStatus" value=${session.isOn()}>
        </form>

    </div>

    <div class="inner-wrapper">

        <p>
            Dependent options list:
            <c:choose>
            <c:when test="${option.getDependentOptions().size() != 0}">
            <a class="inline-link-edit" style="padding-right: 10px" title="Edit option dependencies" href="#" onclick="document.getElementById('formId4').submit()"></a>
            <a class="inline-link-delete" title="Clear all dependencies" href="#" onclick="document.getElementById('formId5').submit()"></a>

            <form id="formId5" method="post" action="removeAllDependentOptions" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value="${option.id}">
                <input type="hidden" name="tariffId" value="${tariff.id}">
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
            </form>

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
                    <c:forEach var="dependentOption" items="${option.getDependentOptions()}">
                        <tr>
                            <td>
                                ${dependentOption.id}
                            </td>
                            <td>
                                ${dependentOption.title}
                            </td>
                            <td>
                                ${dependentOption.price}
                            </td>
                            <td>
                                ${dependentOption.costOfConnection}
                            </td>
                            <td style="width: 0">
                                <form id="formId5${dependentOption.id}" method="post" action="removeDependentOption" enctype="application/x-www-form-urlencoded">
                                    <input type="hidden" name="id" value="${option.id}">
                                    <input type="hidden" name="dependentOptionId" value="${dependentOption.id}">
                                    <input type="hidden" name="tariffId" value="${tariff.id}">
                                    <input type="hidden" name="sessionRole" value=${session.role}>
                                    <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                    <a class="inline-link-delete" title="Remove dependency" href="#" onclick="document.getElementById('formId5${dependentOption.id}').submit()"></a>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                </c:when>
                <c:otherwise>
                    empty. <a class="inline-link-edit" title="Edit option dependencies" href="#" onclick="document.getElementById('formId4').submit()"></a>
                </c:otherwise>
        </c:choose>

    </div>

    <div class="inner-wrapper">

        <p>
            Incompatible options list:
            <c:choose>
            <c:when test="${option.getIncompatibleOptions().size() != 0}">
            <a class="inline-link-edit" style="padding-right: 10px" title="Edit option dependencies" href="#" onclick="document.getElementById('formId4').submit()"></a>
            <a class="inline-link-delete" title="Clear all incompatibilities" href="#" onclick="document.getElementById('formId6').submit()"></a>

        <form id="formId6" method="post" action="removeAllIncompatibleOptions" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value="${option.id}">
            <input type="hidden" name="tariffId" value="${tariff.id}">
            <input type="hidden" name="sessionRole" value=${session.role}>
            <input type="hidden" name="sessionStatus" value=${session.isOn()}>
        </form>

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
            <c:forEach var="incompatibleOption" items="${option.getIncompatibleOptions()}">
                <tr>
                    <td>
                            ${incompatibleOption.id}
                    </td>
                    <td>
                            ${incompatibleOption.title}
                    </td>
                    <td>
                            ${incompatibleOption.price}
                    </td>
                    <td>
                            ${incompatibleOption.costOfConnection}
                    </td>
                    <td style="width: 0">
                        <form id="formId6${incompatibleOption.id}" method="post" action="removeIncompatibleOption" enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="id" value="${option.id}">
                            <input type="hidden" name="incompatibleOptionId" value="${incompatibleOption.id}">
                            <input type="hidden" name="tariffId" value="${tariff.id}">
                            <input type="hidden" name="sessionRole" value=${session.role}>
                            <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                            <a class="inline-link-delete" title="Remove incompatibility" href="#" onclick="document.getElementById('formId6${incompatibleOption.id}').submit()"></a>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
            empty. <a class="inline-link-edit" title="Edit option dependencies" href="#" onclick="document.getElementById('formId4').submit()"></a>
        </c:otherwise>
        </c:choose>

    </div>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
