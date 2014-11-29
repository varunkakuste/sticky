<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Sticky Notes</title>
</head>
<body>
	<form:form method="get" action="upload" modelAttribute="filesForm">
		<table>
			<tr>
				<td colspan="3">
					<h4>Files/Folders on the Dropbox</h4>
				</td>
			</tr>
			<c:forEach var="data" items="${filesForm.filesToList}" varStatus="loop">
				<tr>
					<td>
						${data}
					</td>
					<td>
						Edit
					</td>
					<td>
						Delete
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="right" colspan="3">
					<input type="submit" value="Upload">
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>