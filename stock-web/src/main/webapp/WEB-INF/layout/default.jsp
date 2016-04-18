<%@page contentType="text/html" pageEncoding="ISO-8859-2"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!doctype html>

<html lang="pl-PL">
    <head>
        <meta charset="ISO-8859-2">
        <meta http-equiv="X-UA-Compatible" content="IE=6; IE=7; IE=8; IE=9; IE=EDGE" >
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <sec:csrfMetaTags />
    <title><tiles:insertAttribute name="title"/></title>
    <meta name="description" content="Stock">
    <meta name="author" content="TOM-12">


    <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/resources/jquery/jquery-ui.min.js' />"></script>


    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/jquery/jquery-ui.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/jquery/jquery-ui.theme.min.css'/>" rel="stylesheet"/>


</head>

<body>
    <div class="container-fluid">
        <tiles:insertAttribute name="header"/>

    </div>
    <main>     
        <div class="container-fluid">
            <tiles:insertAttribute name="body"/>            
        </div>
    </main>
    <div class="container-fluid">
        <hr/>
        <tiles:insertAttribute name="footer"/>
    </div>
</body>
</html>
