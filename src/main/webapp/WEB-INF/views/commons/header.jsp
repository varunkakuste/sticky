<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>
</head>
<body>
	<form:form method="get" action="login" modelAttribute="filesForm">
	<table>
		<tr>
			<td>
				<h3>Welcome ${filesForm.loggedInUser}</h3>
			</td>
			<td>
				<a href="#">Logout</a>
			</td>
		</tr>
	</table>
	</form:form>
</body>
</html>