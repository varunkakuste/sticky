<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<form:form method="post" action="home" modelAttribute="loginForm">
		<table align="center">
			<tr>
				<td colspan="2" align="center">
					<h3>Sticky Notes Login</h3>
				</td>
			</tr>
			<tr>
				<td>Enter App Key: </td>
				<td><form:input path="appKey" /></td>
			</tr>
			<tr>
				<td>Enter App Secret: </td>
				<td><form:input path="appSecret" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="Login">
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>