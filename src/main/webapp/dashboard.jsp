<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ru.tsystems.tsproject.ecare.entities.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>Operator's dashboard</title>
</head>
<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="header.jsp"></jsp:include>

    <div class="inner-wrapper">

        <p>
            List of clients:

            <c:choose>
                <c:when test="${clientsList.size() != 0}">
                </p>
                <br>
                    <table>
                        <tr>
                            <th>
                                Client ID
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Passport
                            </th>
                            <th>
                                E-mail
                            </th>
                            <th style="width: 0">
                                    ${HtmlUtil.EMPTY_TD}
                            </th>
                        </tr>
                        <c:forEach var="client" items="${clientsList}">
                            <tr>
                                <td>

                                    <form id="formId2${client.id}" method="post" action="client" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${client.id}>
                                        <input type="hidden" name="action" value="viewClient">
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link" href="#" onclick="document.getElementById('formId2${client.id}').submit()">${client.id}</a>
                                    </form>

                                </td>
                                <td>
                                        ${client.name}
                                </td>
                                <td>
                                        ${client.passport}
                                </td>
                                <td>
                                        ${client.email}
                                </td>
                                <td style="width: 0">

                                    <form id="formId3${client.id}" method="post" action="dashboard" enctype="application/x-www-form-urlencoded">
                                        <input type="hidden" name="id" value=${client.id}>
                                        <input type="hidden" name="action" value="deleteClient">
                                        <input type="hidden" name="sessionRole" value=${session.role}>
                                        <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                                        <a class="inline-link-delete" title="Delete client" href="#" onclick="document.getElementById('formId3${client.id}').submit()"></a>
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

    <jsp:include page="footer.jsp"></jsp:include>

</div>

</body>

</html>
