<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<tags:common title="movie_cart">
		<c:set var="totalPrice" value="0" />	
        <h1><fmt:message key="movie_cart"/></h1>
        
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
		            <c:set var="totalPrice" value="${totalPrice + filmItem.getCost()}" />
		            <td style="padding: 10px; border: 1px solid #ddd;">
		                <form action="controller" method="post">
		                    <input type="hidden" name="command" value="delete_from_cart" />
		                    <input type="hidden" name="film" value="${filmItem.getId()}" />
		                    <input type="submit" value="<fmt:message key="delete_from_cart"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		                </form>
		            </td>
		        </tr>
		    </c:forEach>
		    </tbody>
		</table>
        <h2><fmt:message key="total_cost"/>: $ ${totalPrice}</h2>
        <h2><fmt:message key="total_cost_discount"/> ${sessionScope.user.getSale() * 100}%: $ ${totalPrice * (1 - sessionScope.user.getSale())}</h2>

		<c:if test="${not (filmList.size() eq 0)}">
	       	<tags:command commandName="buy_films" text="buy_film"/>
		</c:if>
    <style>
    body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-family: Arial, sans-serif;	
            background-color: beige;
        }
    </style>
</tags:common>