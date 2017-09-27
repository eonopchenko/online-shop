<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit product</title>
</head>
<body>
	<%
		Map<String, String> product = (Map<String, String>) request.getAttribute("product");
	%>
	<form action="product" method="post">
		<label>Name:</label><input type="text" name="Name" value="<%=product.get("Name") %>"/>
		<label>Price:</label><input type="text" name="Price" value="<%=product.get("Price") %>"/>
		<label>Stock:</label><input type="text" name="Stock" value="<%=product.get("Stock") %>"/>
		<label>Comment:</label><input type="text" name="Comment" value="<%=product.get("Comment") %>"/>
		<input type="hidden" name="Sid" value="<%=product.get("Sid") %>"/>
		<input type="submit" value="Submit" />
	</form>

</body>
</html>