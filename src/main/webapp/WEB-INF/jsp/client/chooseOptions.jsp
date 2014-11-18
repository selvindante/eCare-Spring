<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/style.css">
    <title>Step 2. Choose options</title>
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
        <p>
            Chosen tariff title: ${tariff.title}
        </p>
        <p>
            Chosen tariff price: ${tariff.price}
        </p>

    </div>

    <div class="inner-wrapper">
        <p>
            Step 2. Choose options for tariff.
        </p>
        <br>
        <p>
            Available options list:
            <c:choose>
            <c:when test="${options.size() != 0}">
        </p>
        <br>
            <form method="post" action="setNewTariff" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="id" value=${contract.id}>
                <input type="hidden" name="tariffId" value=${tariff.id}>
                <input type="hidden" name="sessionRole" value=${session.role}>
                <input type="hidden" name="sessionStatus" value=${session.isOn()}>
                <table>
                    <tr>
                        <th>
                            Option title
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
                    <c:forEach var="option" items="${options}">

                        <tr>
                            <td style="width: 200px">
                                ${option.title}
                            </td>
                            <td>
                                ${option.price}
                            </td>
                            <td>
                                ${option.costOfConnection}
                            </td>
                            <td style="width: 0">
                                <input type="checkbox" class="case" id="box${option.id}" name="options" value="${option.id}">
                            </td>
                            <td id="dep${option.id}" style="display: none;">
                                (will be enabled automatically)
                            </td>
                            <td id="inc${option.id}" style="display: none;">
                                (incompatible with chosen option)
                            </td>
                        </tr>

                        <SCRIPT language="javascript">
                            $(function(){
                                $("#box${option.id}").click(function(){

                                    if($("#box${option.id}").is(":checked")) {

                                        <c:if test="${option.getDependentOptions().size() != 0}">

                                            <c:forEach items="${option.getDependentOptions()}" var="dependentOption">
                                                $("#box${dependentOption.id}").attr("disabled", true);
                                                $("#dep${dependentOption.id}").attr("style", "width: 200; background: rgba(216, 255, 213, 0.38); font-size: 12px; color: #008d47");
                                            </c:forEach>

                                        </c:if>

                                        <c:if test="${option.getIncompatibleOptions().size() != 0}">

                                            <c:forEach items="${option.getIncompatibleOptions()}" var="incompatibleOption">
                                                $("#box${incompatibleOption.id}").attr("disabled", true);
                                                $("#inc${incompatibleOption.id}").attr("style", "width: 200; background: rgba(255, 232, 232, 0.52); font-size: 12px; color: #C90000");
                                            </c:forEach>

                                        </c:if>

                                    } else {

                                        <c:if test="${option.getDependentOptions().size() != 0}">

                                            <c:forEach items="${option.getDependentOptions()}" var="dependentOption">
                                                $("#box${dependentOption.id}").removeAttr("disabled");
                                                $("#dep${dependentOption.id}").attr("style", "display: none;");
                                            </c:forEach>

                                        </c:if>

                                        <c:if test="${option.getIncompatibleOptions().size() != 0}">

                                            <c:forEach items="${option.getIncompatibleOptions()}" var="incompatibleOption">
                                                $("#box${incompatibleOption.id}").removeAttr("disabled");
                                                $("#inc${incompatibleOption.id}").attr("style", "display: none;");
                                            </c:forEach>

                                        </c:if>
                                    }

                                });
                            });
                        </SCRIPT>

                    </c:forEach>
                </table>
                <br>
                <button type="submit" class="modern">Save</button>
            </form>
        </c:when>
        <c:otherwise>
            empty.
            </p>
        </c:otherwise>
        </c:choose>
    </div>

    <c:if test="${options.size() != 0}">

        <div class="inner-wrapper-info">

            <p>
                Dependencies of options:
            </p>
            <br>

            <c:forEach var="currentOption1" items="${options}">

                <c:if test="${currentOption1.getDependentOptions().size() != 0}">

                    <p>
                        Option ${currentOption1.title} will be enabled with options:

                        <c:forEach var="depOption" items="${currentOption1.getDependentOptions()}">

                            ${depOption.title};
                            &nbsp;

                        </c:forEach>

                    </p>

                </c:if>

            </c:forEach>

        </div>

        <div class="inner-wrapper-info">

            <p>
                Incompatibilities of options:
            </p>
            <br>

            <c:forEach var="currentOption2" items="${options}">

                <c:if test="${currentOption2.getIncompatibleOptions().size() != 0}">

                    <p>
                        Option ${currentOption2.title} is incompatible with options:

                        <c:forEach var="incOption" items="${currentOption2.getIncompatibleOptions()}">

                            ${incOption.title};&nbsp

                        </c:forEach>

                    </p>

                </c:if>

            </c:forEach>

        </div>

    </c:if>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>

</div>

</body>
</html>
