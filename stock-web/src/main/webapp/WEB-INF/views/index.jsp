<%-- 
    Document   : main
    Created on : 2016-04-15, 19:39:19
    Author     : TOM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

        <h1>Welcome</h1>

        <p>Please login or register</p>
        <form name='loginForm'
              action="<c:url value='/j_spring_security_check'/>" method='POST'>
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
                    <td colspan='2'><input name="submit" type="submit" value="submit" /></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />
        </form>

        <a href="<c:url value='/register'/>">Register</a>
        
