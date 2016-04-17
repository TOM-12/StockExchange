<%-- 
    Document   : StockPrices
    Created on : 2016-04-17, 15:07:41
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">

    function doAjax() {
        $.ajax({
            dataType: 'json',
            url: '${pageContext.request.contextPath}/stock/currentStock',
            type: 'GET',
            success: function (data) {
                var a = data.publicationDate;
                $('#test').html(a);

            },
            complete: function () {
                setTimeout(doAjax, 5000);
            }
        });
    }
    function populateTable(obj) {
        var r = new Array();
        var j = -1;
        r[++j] = '<table id="stocksTable" class="table table-bordered"><thead><tr><th>Company</th><th>Value</th><th>Actions</th></tr></thead>';

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
<div>

    <hr/>
    <div id="test">
    </div>
    <hr/>
    <div id="test2">
    </div>
    <hr/>
    <div id="curentStock">
    </div>

</div>
