<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload File</title>
</head>
<body>

	<form:form method="POST" enctype="multipart/form-data" action="/savefile">
		<table align="center">
			<tr>
				<td>
					File to upload:
				</td>
				<td>
					<input type="file" name="file">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="Upload File">
				</td>
			</tr>
		</table>
		
	</form:form>
</body>
</html>