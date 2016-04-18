
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

    function doAjax() {
        $.ajax({
            dataType: 'json',
            url: '${pageContext.request.contextPath}/stock/ajax',
            type: 'GET',
            success: function (data) {
                populateWaletTable(data.wallet,data.status);
                populateCurrentRatesTable(data.publication,data.status);
                $('#test').html(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#wallet').html("Connection to service failed");
                $('#curentStock').html("Connection to service failed");
            },
            complete: function () {
                setTimeout(doAjax, 5000);
            }
        });
    }
    function populateWaletTable(obj,status) {
        var r = new Array();
        var j = -1;
        if(!status){
            r[++j] = '<h3>';
            r[++j] = 'Connection to service failed. Can\'t get current exchange rates.';            
            r[++j] = 'Displaying latest:';            
            r[++j] = '</h3>';
        }
        r[++j] = '<table id="walletTable" class="table table-bordered"><thead><tr><th>Company</th><th>Unit price [PLN]</th><th>Amount</th><th>Value [PLN]</th><th>Actions</th></tr></thead>';

        for (var i in obj.stocks) {
            var item = obj.stocks[i];
            r[++j] = '<tr>';
            r[++j] = '<td>';
            r[++j] = item.code;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = item.price;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = item.walletAmount;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = item.value;
            r[++j] = '</td>';
            r[++j] = '<td>';
            r[++j] = 'action';
            r[++j] = '</td>';
            r[++j] = '</tr>';
        }
        r[++j] = '</table>';
        r[++j] = '<hr/>';
        r[++j] = '<div>';
        r[++j] = '<label>';
        r[++j] = 'Available money: ';
        r[++j] = obj.money;
        r[++j] = ' PLN';
        r[++j] = '</label>';
        r[++j] = '</div>';

        $('#wallet').html(r.join(''));
    }
    function populateCurrentRatesTable(obj,status) {
        var r = new Array();
        var j = -1;
        if(!status){
            r[++j] = '<h3>';
            r[++j] = 'Connection to service failed. Can\'t get current exchange rates.';            
            r[++j] = 'Displaying latest:';            
            r[++j] = '</h3>';
        }
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
                populateCurrentRatesTable(data);
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