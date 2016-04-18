<%-- 
    Document   : register
    Created on : 2016-04-15, 20:00:06
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div>
    <div>
        Successfully created user "${firstName} ${lastName}" with login "${login}"
    </div>
    <div>
        <button role="group" id="submit" type="button" class="btn btn-secondary- btn-md" onclick="location.href = '${pageContext.request.contextPath}/index ';">
            <i class="fa fa-arrow-left">
            </i>
            Return to main page
        </button>
    </div>
</div>