<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendant</title>
</head>
<body>
<a href="attendant?id=0&action=update">Add New</a>
<table>
	<tr>
		<td>
			ID
		</td>
		<td>
			Name
		</td>
		<td>
			Mobile Number
		</td>
		<td>
			Address
		</td>
		<td>
			Comments
		</td>
		<td>
			Action
		</td>		
	</tr>
	<%
		ArrayList<Map<String, String>> attendats = (ArrayList<Map<String, String>>) request.getAttribute("attendants");
		for (int i = 0; i < attendats.size(); i++) {
	%>
	<tr>
		<td>
		   <%= attendats.get(i).get("Sid") %>
		</td>
		<td>
		   <%= attendats.get(i).get("Name") %>
		</td>
		<td>
		   <%= attendats.get(i).get("Mobile")%>
		</td>
		<td>
		   <%= attendats.get(i).get("Address")%>
		</td>
		<td>
		   <%= attendats.get(i).get("Comment")%>
		</td>
		<td>
			<a href="attendant?id=<%=attendats.get(i).get("Sid") %>&action=update">Edit</a>
			<a href="attendant?id=<%=attendats.get(i).get("Sid") %>&action=delete">Delete</a>
		</td>
	</tr>
	<% 	} %>
</table>

</body>
</html>