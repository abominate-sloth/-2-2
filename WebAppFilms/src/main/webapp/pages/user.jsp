<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<tags:common title="user_title">
	<h1><fmt:message key="film_list"/></h1>
    <table style="width: 100%; border-collapse: collapse;">
    <thead style="background-color: #f2f2f2;">
        <tr>
            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_name"/></th>
            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_genre"/></th>
            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_length"/></th>
            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_cost"/></th>
            <th style="padding: 10px; border: 1px solid #ddd;"></th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${filmList}" var="filmItem">
        <tr>
            <td style="padding: 10px; border: 1px solid #ddd;">${filmItem.getTitle()}</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${filmItem.getGenre()}</td>
            <td style="padding: 10px; border: 1px solid #ddd;">${filmItem.getLength()}</td>
            <td style="padding: 10px; border: 1px solid #ddd;">$ ${filmItem.getCost()}</td>
            <td style="padding: 10px; border: 1px solid #ddd;">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="to_film" />
                    <input type="hidden" name="film" value="${filmItem.getId()}" />
                    <input type="submit" value="<fmt:message key="details"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    <c:if test="${not (page eq 1)}">
    	<form action="controller" method="post" style="margin: 10px;">
			<input type="hidden" name="command" value="to_user" />
			<input type="hidden" name="page" value="${page - 1}" />
			<input type="submit" value="<fmt:message key="previous_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		</form>
    </c:if>
    <c:if test="${not ((page * filmsOnPage) ge filmsNumber)}">
    	<form action="controller" method="post" style="margin: 10px;">
			<input type="hidden" name="command" value="to_user" />
			<input type="hidden" name="page" value="${page + 1}" />
			<input type="submit" value="<fmt:message key="next_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		</form>
    </c:if>
    <br/>
    <c:if test="${sessionScope.user.isAdmin() eq true}">
	    <form action="controller" method="post" style="margin: 10px;">
			<input type="hidden" name="command" value="to_admin" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageUser" value="1" />
			<input type="submit" value="<fmt:message key="to_admin_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		</form>
    </c:if>
    <form action="controller" method="post" style="margin: 10px;">
		<input type="hidden" name="command" value="to_cart" />
		<input type="submit" value="<fmt:message key="to_cart"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
	</form>
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