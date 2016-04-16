<%-- 
    Document   : stock
    Created on : 2016-04-15, 23:08:34
    Author     : TOM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock</title>

        <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/resources/js/jquery.dataTables.min.js' />"></script>


        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>

        <script type="text/javascript">
            window.setInterval(doAjax, 10000);
            window.setInterval(doCurrentStock, 10000);

            function doAjax() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/stock/test',
                    type: 'GET',
                    success: function (data) {
                        $('#test').html(data);
                    }
                });
            }

            function doCurrentStock() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/stock/currentStock',
                    type: 'GET',
                    success: function (data) {
                        $('#curentStock').html(data);
                    }
                });
            }
        </script>

    </head>
    <body>
        <div>
            <h1> Stocks</h1>
        </div>

        <div>
            <h2>Stock prices</h2>

            <div id="test">
            </div>
            <hr/>
            <div id="curentStock">
            </div>

        </div>  
        <div>
            <h2>My wallet</h2>

        </div>  
    </body>
</html>
