<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
</head>
<body>

    <c:url value="/j_spring_security_logout" var="logoutUrl" />

    <form id="logoutForm" method="post" action="${logoutUrl}" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="sessionRole" value=${role}>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>

    <form id="h3TariffsForm" method="post" action="viewAllTariffs" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <form id="h3DashboardForm" method="post" action="viewDashboard" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <form id="h3ClientForm" method="post" action="viewClient" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value=${client.id}>
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <form id="h3TariffForm" method="post" action="viewTariff" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value=${tariff.id}>
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <form id="h3OptionForm" method="post" action="viewOption" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value=${option.id}>
        <input type="hidden" name="tariffId" value=${tariff.id}>
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <form id="h3ContractForm" method="post" action="viewContract" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value=${contract.id}>
        <input type="hidden" name="sessionRole" value=${role}>
    </form>

    <h3>
        <div class="h3-logo"></div>
        <a href="#" onclick="document.getElementById('logoutForm').submit()" class="h3-link" style="border-right-color: rgb(234, 234, 234)">EXIT</a>

        <c:if test="${role == 'ROLE_ADMIN'}">

            <a class="h3-link" href="#" onclick="document.getElementById('h3DashboardForm').submit()">DASHBOARD</a>
            <a class="h3-link" href="#" onclick="document.getElementById('h3TariffsForm').submit()">TARIFFS</a>

            <c:if test="${pagename == 'New contract'}">
                <a class="h3-link" href="#" onclick="document.getElementById('h3ClientForm').submit()">CLIENT</a>
            </c:if>

            <c:if test="${pagename == 'Option' || pagename == 'New option'}">
                <a class="h3-link" href="#" onclick="document.getElementById('h3TariffForm').submit()">TARIFF</a>
            </c:if>

            <c:if test="${pagename == 'Option settings'}">
                <a class="h3-link" href="#" onclick="document.getElementById('h3TariffForm').submit()">TARIFF</a>
                <a class="h3-link" href="#" onclick="document.getElementById('h3OptionForm').submit()">OPTION</a>
            </c:if>

        </c:if>

        <c:if test="${pagename == 'Contract' || pagename == 'Edit client'}">
            <a class="h3-link" href="#" onclick="document.getElementById('h3ClientForm').submit()">CLIENT</a>
        </c:if>

        <c:if test="${pagename == 'Choose tariff' || pagename == 'Choose options'}">
            <a class="h3-link" href="#" onclick="document.getElementById('h3ContractForm').submit()">CONTRACT</a>
        </c:if>

    </h3>

    <c:choose>
        <c:when test="${role == 'ROLE_ADMIN'}">

            <div class="inner-wrapper-header">

                    ${pagename}

                    <a class="inline-link-search" title="Search" style="float: right; margin-top: -3px;" href="#" onclick="document.getElementById('IWHSearchForm').submit()"></a>

                    <form id="IWHSearchForm" style="float: right; margin-top: -5px;" method="post" action="searchClientByNumber" enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="sessionRole" value=${role}>
                    <input type="text" placeholder="search client by number" class="simple-input" name="number" size=20 value="">
                </form>

            </div>

        </c:when>
        <c:otherwise>

            <div class="inner-wrapper-header" style="padding: 15px 30px 15px;">

                ${pagename}

            </div>

        </c:otherwise>
    </c:choose>

    <c:if test="${errormessage != null}">
        <div class="inner-wrapper-error" id="error">
            <p>
                Error: ${errormessage}
            </p>
        </div>

        <SCRIPT language="javascript">
            setInterval(function() {
                $("#error").attr("hidden", true);
            }, 5000);
        </SCRIPT>

    </c:if>

    <c:if test="${successmessage != null}">
        <div class="inner-wrapper-success" id="success">
            <p>
                Success: ${successmessage}
            </p>
        </div>

        <SCRIPT language="javascript">
            setInterval(function() {
                $("#success").attr("hidden", true);
            }, 5000);
        </SCRIPT>

    </c:if>

</body>
</html>
