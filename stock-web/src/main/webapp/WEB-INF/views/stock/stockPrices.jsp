<%-- 
    Document   : StockPrices
    Created on : 2016-04-17, 15:07:41
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    function prepareDialog() {
//        var dialog, form,
//                name = $("#name"),
//                email = $("#email"),
//                password = $("#password"),
//                allFields = $([]).add(name).add(email).add(password)
//                ;
//
//        function addUser() {
//            var valid = true;
//            dialog.dialog("close");
//            return valid;
//        }
//
//        dialog = $("#buy-stock-dialog").dialog({
//            autoOpen: false,
//            height: 300,
//            width: 350,
//            modal: true,
//            buttons: {
//                "Create an account": addUser,
//                Cancel: function () {
//                    dialog.dialog("close");
//                }
//            },
//            close: function () {
//                form[ 0 ].reset();
//                allFields.removeClass("ui-state-error");
//            }
//        });
//
//        form = dialog.find("form").on("submit", function (event) {
//            event.preventDefault();
//            addUser();
//        });

//        $("button[id^='buy-stock-']").button().on("click", function () {
////            alert($(this).attr("id"));
//            var title = ' Buy stock' + $(this).attr("id");
//            $("#buy-stock-dialog").dialog({
//                title: title
//            });
//        });
    }
    ;
</script>
<div>
    <div>
        <h2>Stock prices</h2>
    </div>
    <div id="curentStock">
    </div>
</div>

<div id="buy-stock-dialog-3" style="display: none" title="title">
    <form>
        <sec:csrfInput/>
        <text>Buy </text><input  name="amount" type="number" maxlength="11"/><text id="buy-dialog-3-text"> stocks x  XX XXX
            Base price is XX per XX</text>
    </form>
</div>

<button  name="foo" value="upvote" onclick="post();">Upvote</button>

<script>

</script>