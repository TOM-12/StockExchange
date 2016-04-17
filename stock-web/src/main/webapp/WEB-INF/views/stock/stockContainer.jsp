
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<script>

</script>
<div class="row">

    <div class="container-fluid col-sm-6" style="background-color:  deepskyblue">
        <tiles:insertAttribute  name="stockPrices"/>            
    </div>



    <div class="container-fluid col-sm-6" style="background-color: limegreen">
        <tiles:insertAttribute name="wallet"/>             
    </div>

</div>