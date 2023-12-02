<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<tags:common title="film_page">
    <div class="film-info">
        <h1><fmt:message key="film_page"/></h1>
        
        <h2><fmt:message key="film_name"/>: <c:out value="${film.getTitle()}" /></h2>
        <p><fmt:message key="film_genre"/>: <c:out value="${film.getGenre()}" /></p>
        <p><fmt:message key="film_length"/>: <c:out value="${film.getLength()}" /></p>
        <p><fmt:message key="film_cost"/>: <c:out value="$ ${film.getCost()}" /></p>
        
        <h3><fmt:message key="film_description"/>:</h3>
        <p><c:out value="${film.getDescription()}" /></p>
    </div>
    
    <div class="reviews-section">
        <h3><fmt:message key="reviews"/>:</h3>
        <c:forEach items="${reviewsList}" var="review">
            <div class="review-details">
                <p><fmt:message key="user"/>: <c:out value="${review.getUsername()}" /></p>
                <p><fmt:message key="stars"/>: <c:out value="${review.getStars()}" /></p>
                <p><fmt:message key="review"/>: <c:out value="${review.getText()}" /></p>
                <p><fmt:message key="likes"/>: <c:out value="${review.getLikes()}" /></p>
                <p><fmt:message key="dislikes"/>: <c:out value="${review.getDislikes()}" /></p>
            </div>
        
            <ul class="review-actions">
            	<c:set var="status" value="${commentStatus.get(review.getId())}" />
				<c:if test="${status eq -1}">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="like">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="hidden" name="likes" value="${review.getLikes()}" />
                        <input type="submit" value="<fmt:message key="like"/>" />
                    </form>
                </li>
                </c:if>
                <c:if test="${status eq -1}">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="dislike">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="hidden" name="dislikes" value="${review.getDislikes()}" />
                        <input type="submit" value="<fmt:message key="dislike"/>" />
                    </form>
                </li>
                </c:if>
                <c:if test="${status eq 0}">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="dislike">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="hidden" name="dislikes" value="${review.getDislikes()}" />
                        <input type="submit" value="<fmt:message key="cancel_dislike"/>" />
                    </form>
                </li>
                </c:if>
                <c:if test="${status eq 1}">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="like">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="hidden" name="likes" value="${review.getLikes()}" />
                        <input type="submit" value="<fmt:message key="cancel_like"/>" />
                    </form>
                </li>
                </c:if>
            </ul>
        
            <c:if test="${not empty sessionScope.user and review.getUsername() eq sessionScope.user.getLogin()}">
                <div class="comment-actions">
                    <form action="controller" method="post" style="margin: 10px;">
                        <input type="hidden" name="command" value="show_edit_comment">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="submit" value="<fmt:message key="edit"/>" />
                    </form>
                    
                    <c:if test="${not empty editFlag and editFlag eq review.getId()}">
                        <form action="controller" method="post" style="margin: 10px;">
                            <input type="hidden" name="command" value="edit_comment">
                            <input type="hidden" name="film" value="${film.getId()}" />
                            <input type="hidden" name="reviewId" value="${review.getId()}" />
                            <label for="stars">Оценка:</label>
                            <input type="number" id="stars" name="stars" min="1" max="5" value="${review.getStars()}" /><br/>
                            <label for="comment">Отзыв:</label>
                            <textarea id="comment" name="comment">${review.getText()}</textarea><br/>
                            <input type="submit" value="<fmt:message key="apply"/>" />
                        </form>
                    </c:if>
                    
                    <form action="controller" method="post" style="margin: 10px;">
                        <input type="hidden" name="command" value="delete_comment">
                        <input type="hidden" name="film" value="${film.getId()}" />
                        <input type="hidden" name="reviewId" value="${review.getId()}" />
                        <input type="submit" value="<fmt:message key="delete"/>" />
                    </form>
                </div>
            </c:if>
        
            <hr/>
        </c:forEach>
    </div>
    
    <c:if test="${not empty sessionScope.user}">
        <div class="add-review">
            <h3><fmt:message key="add_review"/>:</h3>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="add_comment">
                <input type="hidden" name="film" value="${film.getId()}" />
                <label for="stars"><fmt:message key="stars"/>:</label>
                <input type="number" id="stars" name="stars" min="1" max="5" /><br/>
                <label for="comment"><fmt:message key="review"/>:</label>
                <textarea id="comment" name="comment"></textarea><br/>
                <input type="submit" value="<fmt:message key="add"/>" />
            </form>
        </div>
    </c:if>
    
    <div class="order-section">
    	<c:choose>
    		<c:when test="${filmStatus eq -1}">
		        <h3><fmt:message key="order_film"/>:</h3>
		        <form action="controller" method="post">
		            <input type="hidden" name="command" value="add_to_cart">
		            <input type="hidden" name="film" value="${film.getId()}" />
		            <input type="submit" value="<fmt:message key="order"/>" />
		        </form>
	        </c:when>
	        <c:otherwise>
	        	<c:if test="${filmStatus eq 0}">
	        		<h3><fmt:message key="film_in_cart"/></h3>
	        	</c:if>
	        	<c:if test="${filmStatus eq 1}">
	        		<h3><fmt:message key="film_buyed"/></h3>
	        	</c:if>
	        </c:otherwise>
	    </c:choose>
    </div>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-family: Arial, sans-serif; 
            background-color: beige;
        }
        
        .film-info {
            margin-bottom: 20px;
            text-align: center;
        }
        
        .reviews-section {
            margin-bottom: 20px;
        }
        
        .review-details {
            margin-bottom: 10px;
        }
        
        .review-actions {
            list-style: none;
            padding: 0;
            display: flex;
            justify-content: center;
        }
        
        .review-actions li {
            margin: 0 5px;
        }
        
        .comment-actions {
            margin-top: 10px;
        }
        
        .add-review {
            margin-top: 20px;
            text-align: center;
        }
        
        .order-section {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</tags:common>