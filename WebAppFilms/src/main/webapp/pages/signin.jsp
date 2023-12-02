<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<tags:common title="signin_title">
    <h1><fmt:message key="signin_page"/></h1>
    
    <form action="controller" method="post">
    	<input type="hidden" name="command" value="sign_in" />
        <label for="login"><fmt:message key="login"/>:</label>
        <input type="text" name="login" id="login" required><br>
        <label for="password"><fmt:message key="password"/>:</label>
        <input type="password" name="password" id="password" required><br>
        <input type="submit" value="<fmt:message key="signin"/>">
    </form>
    
    <c:if test="${not empty failedSignIn}">
        <p style="color: red;">${failedSignIn}</p>
    </c:if>
    <style>
    body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-family: Arial, sans-serif;	
            overflow: hidden;
            background-color: beige;
        }
    </style>
</tags:common>