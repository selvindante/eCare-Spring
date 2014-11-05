<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>Edit option</title>
</head>

<body>

<div class="outer-wrapper clearfix">

    <jsp:include page="header.jsp"></jsp:include>

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
        <br>

    </div>

    <div class="inner-wrapper">

        <form id="formId3" method="post" action="option" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="id" value="${option.id}">
            <input type="hidden" name="tariffId" value="${tariff.id}">
            <input type="hidden" name="action" value="updateOption">
            <input type="hidden" name="sessionRole" value=${session.role}>
            <input type="hidden" name="sessionStatus" value=${session.isOn()}>
            <p>
                Choose dependent options:
                <c:choose>
                    <c:when test="${tariff.getOptions().size() != 0}">
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
                            <c:forEach var="dependentOption" items="${tariff.getOptions()}">
                                <c:if test="${option.id != dependentOption.id}">
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
                                            <c:choose>
                                                <c:when test="${option.getDependentOptions().contains(dependentOption)}">
                                                    <input type="checkbox" id="box1${dependentOption.id}" name="dependentOptions" value="${dependentOption.id}" checked="checked">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" id="box1${dependentOption.id}" name="dependentOptions" value="${dependentOption.id}">
                                                </c:otherwise>
                                            </c:choose>
                                            <span id="inc1${dependentOption.id}" style="display: none;">
                                                (incompatible)
                                            </span>
                                        </td>
                                    </tr>

                                    <SCRIPT language="javascript">
                                        $(function(){
                                            $("#box1${dependentOption.id}").click(function(){

                                                if($("#box1${dependentOption.id}").is(":checked")) {

                                                    $("#box2${dependentOption.id}").attr("disabled", true);
                                                    $("#inc2${dependentOption.id}").attr("style", "font-size: 12px; color: lightgray");

                                                } else {

                                                    $("#box2${dependentOption.id}").removeAttr("disabled");
                                                    $("#inc2${dependentOption.id}").attr("style", "display: none;");
                                                }

                                            });
                                        });
                                    </SCRIPT>

                                </c:if>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        empty.
                        </p>
                        <br>
                    </c:otherwise>
                </c:choose>

            <hr>

            <br>

            <p>
                Choose incompatible options:
                <c:choose>
                <c:when test="${tariff.getOptions().size() != 0}">
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
                <c:forEach var="incompatibleOption" items="${tariff.getOptions()}">
                    <c:if test="${option.id != incompatibleOption.id}">
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
                                <c:choose>
                                    <c:when test="${option.getIncompatibleOptions().contains(incompatibleOption)}">
                                        <input type="checkbox" id="box2${incompatibleOption.id}" name="incompatibleOptions" value="${incompatibleOption.id}" checked="checked">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" id="box2${incompatibleOption.id}" name="incompatibleOptions" value="${incompatibleOption.id}">
                                    </c:otherwise>
                                </c:choose>

                                <span id="inc2${incompatibleOption.id}" style="display: none;">
                                    (dependent)
                                </span>

                                <SCRIPT language="javascript">

                                    if($("#box1${incompatibleOption.id}").is(":checked")) {

                                        $("#box2${incompatibleOption.id}").attr("disabled", true);
                                        $("#inc2${incompatibleOption.id}").attr("style", "font-size: 12px; color: lightgray");

                                    }

                                    if($("#box2${incompatibleOption.id}").is(":checked")) {

                                        $("#box1${incompatibleOption.id}").attr("disabled", true);
                                        $("#inc1${incompatibleOption.id}").attr("style", "font-size: 12px; color: lightgray");

                                    }

                                </SCRIPT>

                            </td>

                        </tr>

                        <SCRIPT language="javascript">
                            $(function(){
                                $("#box2${incompatibleOption.id}").click(function(){

                                    if($("#box2${incompatibleOption.id}").is(":checked")) {

                                        $("#box1${incompatibleOption.id}").attr("disabled", true);
                                        $("#inc1${incompatibleOption.id}").attr("style", "font-size: 12px; color: lightgray");

                                    } else {

                                        $("#box1${incompatibleOption.id}").removeAttr("disabled");
                                        $("#inc1${incompatibleOption.id}").attr("style", "display: none;");
                                    }

                                });
                            });
                        </SCRIPT>

                    </c:if>
                </c:forEach>
            </table>
            </c:when>
            <c:otherwise>
                empty.
                </p>
                <br>
            </c:otherwise>
            </c:choose>

            <button type="submit" class="modern">Save</button>
        </form>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>

</div>

</body>
</html>
