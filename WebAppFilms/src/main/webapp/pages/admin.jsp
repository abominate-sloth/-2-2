<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<tags:common title="admin_page">
        <h1><fmt:message key="admin_page"/></h1>
        
        <h2><fmt:message key="film_list"/></h2>
        <table style="width: 100%; border-collapse: collapse;">
		    <thead style="background-color: #f2f2f2;">
		        <tr>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_name"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_genre"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_length"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="film_cost"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"></th>
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
		                    <input type="hidden" name="command" value="delete_film" />
		                    <input type="hidden" name="film" value="${filmItem.getId()}" />
		                    <input type="hidden" name="page" value="${page}" />
		                    <input type="hidden" name="pageUser" value="${pageUser}" />
		                    <input type="submit" value="<fmt:message key="delete"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		                </form>
		            </td>
		            <td style="padding: 10px; border: 1px solid #ddd;">
		                <form action="controller" method="post">
		                    <input type="hidden" name="command" value="show_edit_film" />
		                    <input type="hidden" name="film" value="${filmItem.getId()}" />
		                    <input type="hidden" name="page" value="${page}" />
		                    <input type="hidden" name="pageUser" value="${pageUser}" />
		                    <input type="submit" value="<fmt:message key="edit"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		                </form>
		            </td>
		        </tr>
		    </c:forEach>
		    </tbody>
		</table>
		<c:if test="${not empty editFilm}">
			<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="edit_film">
				<input type="hidden" name="film" value="${editFilm.getId()}" />
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="pageUser" value="${pageUser}" />
	            <label for="name"><fmt:message key="film_name"/>:</label>
            	<input type="text" name="name" value="${editFilm.getTitle()}" /><br/>
               	<label for="description"><fmt:message key="film_description"/>:</label>
                <textarea id="description" name="description">${editFilm.getDescription()}</textarea><br/>
                <label for="genre"><fmt:message key="film_genre"/>:</label>
                <input type="text" name="genre" value="${editFilm.getGenre()}" /><br/>
                <label for="length"><fmt:message key="film_length"/>:</label>
                <input type="text" name="length" value="${editFilm.getLength()}" /><br/>
                <label for="cost"><fmt:message key="film_cost"/>:</label>
                <input type="number" name="cost" step="any" value="${editFilm.getCost()}" /><br/>
                <input type="submit" value="<fmt:message key="apply"/>" />
            </form>
		</c:if>
		<c:if test="${not (page eq 1)}">
	    	<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="to_admin" />
				<input type="hidden" name="page" value="${page - 1}" />
				<input type="hidden" name="pageUser" value="${pageUser}" />
				<input type="submit" value="<fmt:message key="previous_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
			</form>
	    </c:if>
	    <c:if test="${not ((page * filmsOnPage) ge filmsNumber)}">
	    	<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="to_admin" />
				<input type="hidden" name="page" value="${page + 1}" />
				<input type="hidden" name="pageUser" value="${pageUser}" />
				<input type="submit" value="<fmt:message key="next_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
			</form>
	    </c:if>
		
        <h2><fmt:message key="add_film"/></h2>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="add_film">
            <input type="hidden" name="page" value="${page}" />
            <input type="hidden" name="pageUser" value="${pageUser}" />
			<label for="name"><fmt:message key="film_name"/>:</label>
           	<input type="text" name="name" value="" /><br/>
           	<label for="description"><fmt:message key="film_description"/>:</label>
            <textarea id="description" name="description"></textarea><br/>
            <label for="genre"><fmt:message key="film_genre"/>:</label>
            <input type="text" name="genre" value="" /><br/>
            <label for="length"><fmt:message key="film_length"/>:</label>
            <input type="text" name="length" value="" /><br/>
            <label for="cost"><fmt:message key="film_cost"/>:</label>
            <input type="number" name="cost" step="any" value="0" /><br/>
            <input type="submit" value="<fmt:message key="add"/>" />
        </form>
        
        <h2><fmt:message key="user_list"/></h2>
        <table style="width: 100%; border-collapse: collapse;">
		    <thead style="background-color: #f2f2f2;">
		        <tr>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="user_name"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"><fmt:message key="user_sale"/></th>
		            <th style="padding: 10px; border: 1px solid #ddd;"></th>
		        </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${userList}" var="userItem">
		        <tr>
		            <td style="padding: 10px; border: 1px solid #ddd;">${userItem.getLogin()}</td>
		            <td style="padding: 10px; border: 1px solid #ddd;">${userItem.getSale()}</td>
		            <td style="padding: 10px; border: 1px solid #ddd;">
		                <form action="controller" method="post">
		                    <input type="hidden" name="command" value="show_edit_sale" />
		                    <input type="hidden" name="userId" value="${userItem.getId()}" />
		                    <input type="hidden" name="page" value="${page}" />
		                    <input type="hidden" name="pageUser" value="${pageUser}" />
		                    <input type="submit" value="<fmt:message key="edit_sale"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
		                </form>
		            </td>
		        </tr>
		    </c:forEach>
		    </tbody>
		</table>
		<c:if test="${not empty editUser}">
			<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="edit_sale">
				<input type="hidden" name="userId" value="${editUser.getId()}" />
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="pageUser" value="${pageUser}" />
            	<label for="sale"><fmt:message key="user_name"/>: ${editUser.getLogin()}<br /><fmt:message key="user_sale"/>:</label>
            	<input type="number" step="any" max="1" min="0" name="sale" value="${editUser.getSale()}" /><br/>
                <input type="submit" value="<fmt:message key="apply"/>" />
            </form>
		</c:if>
		<c:if test="${not (pageUser eq 1)}">
	    	<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="to_admin" />
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="pageUser" value="${pageUser - 1}" />
				<input type="submit" value="<fmt:message key="previous_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
			</form>
	    </c:if>
	    <c:if test="${not ((pageUser * usersOnPage) ge usersNumber)}">
	    	<form action="controller" method="post" style="margin: 10px;">
				<input type="hidden" name="command" value="to_admin" />
				<input type="hidden" name="page" value="${page}" />
				<input type="hidden" name="pageUser" value="${pageUser + 1}" />
				<input type="submit" value="<fmt:message key="next_page"/>" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;">
			</form>
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