
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

    function doAjax() {
        $.ajax({
            dataType: 'json',
            timeout: 1000,
            url: '${pageContext.request.contextPath}/stock/ajax',
            type: 'GET',
            success: function (data) {
                populateWaletTable(data.wallet, data.status);
                populateCurrentRatesTable(data.publication, data.status);
                prepareDialog();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#wallet').html("Connection to service failed");
                $('#curentStock').html("Connection to service failed");
            },
            complete: function () {
                setTimeout(doAjax, 2000);
            }
        });
    }
    function populateWaletTable(obj, status) {
        var r = new Array();
        var j = -1;
        if (!status) {
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
    function populateCurrentRatesTable(obj, status) {
        var r = new Array();
        var j = -1;
        if (!status) {
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
            r[++j] = '<button class="dialogBuyOpen"id="buy-stock-';
            r[++j] = item.id;
            r[++j] = '" stock-code="';
            r[++j] = item.code;
            r[++j] = '">Buy</button>';
            r[++j] = '</td>';
            r[++j] = '</td>';
        }
        r[++j] = '</table>';
        $('#curentStock').html(r.join(''));



        var d = new Array();
        var di = -1;
        for (var i in obj.items) {
            var item = obj.items[i];
            d[++di] = '<div id="buy-stock-dialog-' + item.code + '" style="display: none" title="title"><form><sec:csrfInput/>';
            d[++di] = '<div><text>Buy (</text><input  name="amount" type="number" maxlength="11"/><text id="buy-dialog-' + item.id + '-text"></text></div>';
            d[++di] = '<div><input type="checkbox" name="confirmation-checkbox" value="check">I confirm transaction.</div>';
            d[++di] = '</form></div>';
            $('#buy-stock-dialog-' + item.code + '.ui-dialog-content.ui-widget-content').find('text[id="buy-dialog-' + item.id + '-text"]').text(' x ' + item.unit + ') stocks per ' + item.price + ' PLN');

        }
        $('#dialogs').html(d.join(''));

        $('.dialogBuyOpen').click(function () {

            var item = obj.items[$(this).attr('stock-code')];
            var title = 'Buy ' + item.code;
            var $dialog = $("#buy-stock-dialog-" + $(this).attr('stock-code')).dialog({
                autoOpen: true,
                resizable: false,
                title: title,
                height: 200,
                modal: true,
                width: "auto",
                open: function (event, ui) {
                    $(this).find('input[name="amount"]').val(0);
                    $(this).find('text[id="buy-dialog-' + item.id + '-text"]').text(' x ' + item.unit + ') stocks per ' + item.price + ' PLN');
                },
                buttons: {
                    "Buy"
                            : function () {
                                var id = item.id;
                                var amount = $(this).find('input[name="amount"]').val();
                                if ($(this).find('input[name="confirmation-checkbox"]').is(':checked')) {
                                    if (Math.floor(amount) != amount || !$.isNumeric(amount)) {
                                        return false;
                                    } else {
                                        if (amount === 0) {
                                            return false;
                                        } else {
                                            buy(id, amount);
                                            $(this).dialog("close");
                                        }
                                    }
                                } else {
                                    return  false;
                                }
                            }
                },
                close: function (event, ui) {
                    $(this).dialog("destroy");
                }
            });
            $dialog.dialog("open");
        });
    }

    function buy(id, amount) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var url = '${pageContext.request.contextPath}/stock/buy?stockId=' + id + '&stockAmount=' + amount;
        $.ajax({
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            type: 'POST',
            url: url,
            success: function (data) {
                alert(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#curentStock').html("Connection to service failed");
            },
            complete: function () {
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