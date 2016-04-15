<%-- 
    Document   : login
    Created on : 2016-04-15, 21:56:05
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>



        <form name='loginForm'
              action="<c:url value="/j_spring_security_check"  var="loginUrl" />" method='POST'>

            <table>
                <tr>
                    <td>User:</td>
                    <td><input type='text' name='username'></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='password' /></td>
                </tr>
                <tr>
                    <td colspan='2'><input name="submit" type="submit"
                                           value="submit" /></td>
                </tr>
            </table>

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />

        </form>

    </body>
</html>
