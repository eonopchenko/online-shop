<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product</title>
</head>
<body>
<a href="product?id=0&action=update">Add New</a>
<table>
	<tr>
		<td>
			ID
		</td>
		<td>
			Name
		</td>
		<td>
			Price
		</td>
		<td>
			Stock
		</td>
		<td>
			Comments
		</td>
		<td>
			Action
		</td>		
	</tr>
	<%
		ArrayList<Map<String, String>> products = (ArrayList<Map<String, String>>) request.getAttribute("products");
		for (int i = 0; i < products.size(); i++) {
	%>
	<tr>
		<td>
		   <%= products.get(i).get("Sid") %>
		</td>
		<td>
		   <%= products.get(i).get("Name") %>
		</td>
		<td>
		   <%= products.get(i).get("Price")%>
		</td>
		<td>
		   <%= products.get(i).get("Stock")%>
		</td>
		<td>
		   <%= products.get(i).get("Comment")%>
		</td>
		<td>
			<a href="product?id=<%=products.get(i).get("Sid") %>&action=update">Edit</a>
			<a href="product?id=<%=products.get(i).get("Sid") %>&action=delete">Delete</a>
		</td>
	</tr>
	<% 	} %>
</table>

</body>
</html>