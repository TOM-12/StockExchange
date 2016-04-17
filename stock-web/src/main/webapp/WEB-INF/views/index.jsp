<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="panel panel-default col-sm-4 col-sm-offset-4" >

    <form class="form-horizontal" name='loginForm'
          action="<c:url value='/j_spring_security_check'/>" 
          method='POST'>
        <div class="form-group row">
            <label    for="login" class="control-label control-label col-sm-2 col-sm-offset-2">Login:</label>
            <div class="col-sm-6">
                <input type='text'  id="login" name="username" class="form-control" placeholder="Login"  maxlength="45"/>
            </div>
        </div>

        <div class="form-group row">
            <label  for="password"  class="control-label control-label col-sm-2 col-sm-offset-2">Password</label>
            <div class="col-sm-6">
                <input type='password'  id="password" name="password" class="form-control" placeholder="Password"  maxlength="45"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-8 col-sm-offset-2 btn-group btn-group-vertical">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input class="btn btn-primary" name="submit" type="submit" value="Login" />
                <input class="btn btn-secondary" name="register" type="button" value="Register" onclick=" location.href = '${pageContext.request.contextPath}/register ';" />
            </div>
        </div>
    </form>


</div>
