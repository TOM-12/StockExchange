<%-- 
    Document   : stock
    Created on : 2016-04-15, 23:08:34
    Author     : TOM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                var a = data.publicationDate;
                a = a + "---" + data.publicationDate;
                a = a + "<br/>";
                a = a + data.items;

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
