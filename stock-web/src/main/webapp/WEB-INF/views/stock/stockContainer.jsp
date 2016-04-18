
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script type="text/javascript">

    function doAjax() {
        $.ajax({
            url: '${pageContext.request.contextPath}/stock/ajax',
            type: 'GET',
            success: function (data) {
                
                $('#test').html(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {

            },
            complete: function () {
                setTimeout(doAjax, 5000);
            }
        });
    }
    function populateTable(obj) {
        var r = new Array();
        var j = -1;
        r[++j] = 'Current exchange rates are from: ';
        r[++j] = obj.publicationDate;
        r[++j] = '<table id="stocksTable" class="table table-bordered"><thead><tr><th>Company</th><th>Value [PLN]</th><th>Actions</th></tr></thead>';

        for (var i in obj.items) {
            var item = obj.items[i];
            r[++j] = '<tr>';
            r[++j] = '<td>';
            r[++j] = item.code;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = item.price;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = 'action';
            r[++j] = '</td>';
            r[++j] = '</td>';
        }
        r[++j] = '</table>';

        $('#curentStock').html(r.join(''));
    }

    function doCurrentStock() {
        $.ajax({
            dataType: 'json',
            url: '${pageContext.request.contextPath}/stock/currentStock',
            type: 'GET',
            success: function (data) {
                populateTable(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#curentStock').html("Connection to service failed");
            },
            complete: function () {
                setTimeout(doCurrentStock, 5000);
            }
        });
    }
    $(document).ready(function () {
        doAjax();
        doCurrentStock();
    });

</script>
<div class="row">

    <div class="container-fluid col-sm-6" style="background-color:  deepskyblue">
        <tiles:insertAttribute  name="stockPrices"/>            
    </div>



    <div class="container-fluid col-sm-6" style="background-color: limegreen">
        <tiles:insertAttribute name="wallet"/>             
    </div>

</div>