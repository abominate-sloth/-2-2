<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ tag isELIgnored="false" %>
<%@ attribute name="title" required="true" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="resources.messages" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
	    <title><fmt:message key="${title}"/></title>
	    <style>
	    header {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		}
	    
        h1 {
            color: #333;
        }

		a {
		  display: inline-block;
		  padding: 10px 20px;
		  background-color: #4CAF50;
		  color: white;
		  text-decoration: none;
		  border: none;
		  cursor: pointer;
		}

        p {
            font-size: 18px;
            margin-bottom: 10px;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        li {
            display: inline-block;
            margin: 10px;
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
        
        input[type="text"], input[type="password"] {
			  display: block;
			  width: 100%;
			  padding: 10px;
			  font-size: 16px;
			  border: 1px solid #ccc;
			  border-radius: 4px;
			  box-sizing: border-box;
		}

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
	</head>
	<body>
		<header>
			<ul>
				<li>
					<tags:command commandName="to_main" text="main_title"/>
				</li>
			</ul>
			<c:choose>
				<c:when test="${empty sessionScope.user}">
					<ul>
					    <li>
					    	<tags:command commandName="to_registration" text="registration_title"/>
					    </li>
				        <li>
				            <tags:command commandName="to_sign_in" text="signin_title"/>
				        </li>
				    </ul>		    
				</c:when>
				<c:otherwise>
					<ul>

				        <li>
				        	<tags:command commandName="logout" text="logout_title"/>
				        </li>
				    </ul>	
				</c:otherwise>
			</c:choose>
			<ul>
	        	<li><a href="?changeLocale=True"><fmt:message key="change_language"/></a></li>
	        </ul>
		</header>
		<main>
		    <jsp:doBody/>
		</main>
	</body>
</html>