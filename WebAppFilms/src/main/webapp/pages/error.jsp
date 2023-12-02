<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<!DOCTYPE html>
<html>
	<head>
	    <title><fmt:message key="error"/></title>
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
	
	        h1 {
	            color: red;
	            
	        }
	        
	        p {
	            font-size: 18px;
	            margin-bottom: 10px;
	            color: red;
	        }
	        
	        form {
			  display: flex;
			  flex-direction: column;
			  justify-content: center;
			  align-items: center;
			}
	
	        input[type="submit"] {
	            padding: 10px 20px;
	            font-size: 16px;
	            background-color: #4CAF50;
	            color: white;
	            border: none;
	            cursor: pointer;
	        }
	
	        input[type="submit"]:hover {
	            background-color: #45a049;
	        }
	    </style>
	</head>
	<body>
        <h1><fmt:message key="error_occured"/></h1>
        <p>${errorMessage}</p>
        <tags:command commandName="to_main" text="main_title"/>
	</body>
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
</html>