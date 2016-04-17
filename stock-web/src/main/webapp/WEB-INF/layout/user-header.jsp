<%@page contentType="text/html" pageEncoding="ISO-8859-2"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<header class="header row">
    <div class="col-sm-2">
        <h1><tiles:insertAttribute name="title"/></h1>
    </div>
    <sec:authorize access="isAuthenticated()">
        <div class="col-sm-10 row">
            <sec:authentication property="principal.firstName" var="firstName" />
            <sec:authentication property="principal.lastName" var="lastName" />
            <div class="col-sm-offset-7 col-sm-3">             
                <h2>Logged in as ${firstName} ${lastName}</h2>
            </div>  
            <div class="col-sm-1">
                <a href="${pageContext.request.contextPath}/stock/settings"><i class="fa fa-cog fa-3x"></i></a>
            </div> 
            <div class="col-sm-1">
                <c:url value="" var="logoutUrl" />
                <form action="${pageContext.request.contextPath}/j_spring_security_logout" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
                <a href="javascript:formSubmit()" /><i class="fa fa-power-off fa-3x" style="text-align: center" ></i></a>
            </div>  
        </div>
    </sec:authorize>

    <script>
        function formSubmit() {
            document.getElementById("logoutForm").submit();
        }
    </script>
</header>