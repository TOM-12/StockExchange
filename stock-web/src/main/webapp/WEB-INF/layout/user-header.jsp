<%@page contentType="text/html" pageEncoding="ISO-8859-2"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<header class="header">
    <div>
        <h1><tiles:insertAttribute name="title"/></h1>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal.firstName" var="firstName" />
            <sec:authentication property="principal.lastName" var="lastName" />               
            <p>Logged as ${firstName} ${lastName}</p>		
            <a href="${pageContext.request.contextPath}/stock/settings"><i class="fa fa-cog fa-3x"></i></a>
            <c:url value="/j_spring_security_logout" var="logoutUrl" />
            <form action="${logoutUrl}" method="post" id="logoutForm">
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
            </form>
            <a href="javascript:formSubmit()" /><i class="fa fa-power-off fa-3x"></i></a>
        </sec:authorize>
    </div>
</header>