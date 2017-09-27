<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit attendant</title>
</head>
<body>
	<%
		Map<String, String> attendant = (Map<String, String>) request.getAttribute("attendant");
	%>
	<form action="attendant" method="post">
		<label>Name:</label><input type="text" name="Name" value="<%=attendant.get("Name") %>"/>
		<label>Mobile:</label><input type="text" name="Mobile" value="<%=attendant.get("Mobile") %>"/>
		<label>Address:</label><input type="text" name="Address" value="<%=attendant.get("Address") %>"/>
		<label>Comment:</label><input type="text" name="Comment" value="<%=attendant.get("Comment") %>"/>
		<input type="hidden" name="Sid" value="<%=attendant.get("Sid") %>"/>
		<input type="submit" value="Submit" />
	</form>

</body>
</html>