<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag isELIgnored="false" %>
<%@ attribute name="commandName" required="true" %>
<%@ attribute name="text" required="true" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<form action="controller" method="post" style="margin: 10px;">
    <input type="hidden" name="command" value="${commandName}" />
    <input type="submit" value="<fmt:message key="${text}"/>">
</form>