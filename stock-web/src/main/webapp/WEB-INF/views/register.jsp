<%-- 
    Document   : register
    Created on : 2016-04-15, 20:00:06
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<form:form method="POST" modelAttribute="registerForm" id="registerForm" role="form">
    <sec:csrfInput />
    <div class="form-group row">
        <form:label  path="firstName"  cssClass="control-label col-md-1 col-md-offset-4">First name:</form:label>
            <div class="col-md-3">
            <form:input  id="userFirstName" path="firstName" cssClass="form-control" placeholder="First name" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="firstName" cssClass="error" />
        </div>
    </div>

    <div class="form-group row">
        <form:label path="lastName" cssClass="control-label col-md-1 col-md-offset-4">Last name:</form:label>
            <div class="col-md-3">
            <form:input id="userLastName" path="lastName"  cssClass="form-control" placeholder="Last name" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="lastName" cssClass="error" />
        </div>
    </div>

    <div class="form-group row">
        <form:label path="login" cssClass="control-label col-md-1 col-md-offset-4">Login:</form:label>
            <div class="col-md-3">
            <form:input id="userlogin" path="login" cssClass="form-control" placeholder="Login" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="login" cssClass="error" />
        </div>
    </div>

    <div class="form-group row">
        <form:label path="password" cssClass="control-label col-md-1 col-md-offset-4">Password:</form:label>
            <div class="col-md-3">
            <form:password id="userpassword" path="password" cssClass="form-control" placeholder="Password" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="password" cssClass="error" />
        </div>
    </div>

    <div class="form-group row">
        <form:label path="confirmPassword" cssClass="control-label col-md-1 col-md-offset-4">Confirm password:</form:label>
            <div class="col-md-3">
            <form:password id="userconfirmPassword" path="confirmPassword" cssClass="form-control" placeholder="Password" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="confirmPassword" cssClass="error" />
        </div>
    </div>
            <form:errors  path="*"/>
    <div class="form-group row">
        <div class="col-md-6 col-md-offset-3 panel panel-default">
            <div class="row center-block" style="text-align: center">
                <label>Wallet (Exchange rates from ${registerForm.publicationDate}):</label>
            </div>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Company</th>
                        <th>Company code</th>
                        <th>Price [PLN]</th>
                        <th>Base unit</th>
                        <th>Available</th>
                        <th>In wallet</th>
                    </tr>
                </thead>
                <c:forEach items="${registerForm.walletStocks}" var="item" varStatus="idx">
                    <tr>
                        <td>${item.name}</td>
                        <td>${item.code}</td>
                        <td>${item.price}</td>
                        <td>${item.unit}</td>
                        <td>${item.available}</td>
                        <td>
                            <form:input id="walletStocks[${idx.index}].walletAmount" path="walletStocks[${idx.index}].walletAmount" cssClass="form-control" placeholder="0" cssErrorClass="form-control error" maxlength="11"/>
                            <form:errors path="walletStocks[${idx.index}].walletAmount" cssClass="error" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <div class="form-group row">
        <form:label path="money" cssClass="control-label col-md-1 col-md-offset-4">Available money[PLN]:</form:label>
            <div class="col-md-3">
            <form:input id="usermoney" path="money" cssClass="form-control" placeholder="Available money" cssErrorClass="form-control error" maxlength="45"/>
            <form:errors path="money" cssClass="error" />
        </div>
    </div>

    <div class="form-group row text-center ">
        <div class="btn-group btn-group-md" role="group" >
            <button role="group" id="submit" type="button" class="btn btn-secondary- btn-md" onclick="location.href = '${pageContext.request.contextPath}/index ';">
                <i class="fa fa-arrow-left">
                </i>
                Return
            </button>
            <button  role="group"id="submit" type="submit" class="btn btn-primary btn-md">
                Register
            </button>
        </div>
    </div>
</form:form>