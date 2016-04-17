<%-- 
    Document   : stock
    Created on : 2016-04-15, 23:08:34
    Author     : TOM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock</title>

        <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/resources/js/jquery.dataTables.min.js' />"></script>


        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
        <link href="<c:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet"/>

        <script type="text/javascript">

            function doAjax() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/stock/test',
                    type: 'GET',
                    success: function (data) {
                        $('#test').html(data);
                    },
                    complete: function () {
                        setTimeout(doAjax, 5000);
                    }
                });
            }

            function doCurrentStock() {
                $.ajax({
                    dataType: 'json',
                    url: '${pageContext.request.contextPath}/stock/currentStock',
                    type: 'GET',
                    success: function (data) {
                        var a= data.publicationDate;
                        a = a +"---"+data.publicationDate;
                        a=a+ "<br/>";
                        a=a+ data.items;
                        
                        $('#curentStock').html(a);
                    },
                    complete: function () {
                        setTimeout(doCurrentStock, 5000);
                    }
                });
            }
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
            $(document).ready(function () {
                doAjax();
                doCurrentStock();
            });
        </script>

    </head>
    <body>
        <div>
            <h1> Stocks</h1>
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

    <div>
        <h2>Stock prices</h2>

        <div id="test">
        </div>
        <hr/>
        <div id="curentStock">
        </div>



        <div>
            <h2>My wallet</h2>

        </div>  
</body>
</html>
