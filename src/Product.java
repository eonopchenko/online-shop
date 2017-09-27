

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher = null;
	Connection connection = null;

	public Product () {
		super();
	}
	
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\eugene\\Documents\\EclipseWorkspace\\OnlineShop\\onlineshopDB.sqlite");
			System.out.println("Connection successful");
		} catch(Exception e) {
			System.out.println(e);
		}	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			request.setAttribute("products", getAll());
			dispatcher = request.getRequestDispatcher("/product/view.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("action").equals("update")) {
			request.setAttribute("product", getOne(Integer.parseInt(request.getParameter("id"))));
			dispatcher = request.getRequestDispatcher("/product/edit.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("action").equals("delete")) {
			deleteOne(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("product");
		} else {
			dispatcher = request.getRequestDispatcher("/product/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	private ArrayList<Map<String, String>> getAll () {
		String  sid, name, price, stock, comm;
		ArrayList<Map<String, String>> attendats = new ArrayList<Map<String, String>>();
		String query = ("SELECT * FROM Product");
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("Prd_Name");
				price = rs.getString("Prd_Price");
				sid = rs.getString("Prd_ID");
				stock = rs.getString("Prd_Stock");
				comm = rs.getString("Prd_Comments");				
				Map<String, String> map = new HashMap<String, String>();
				map.put("Name", name);
				map.put("Sid", sid);
				map.put("Price", price);
				map.put("Comment", comm);
				map.put("Stock", stock);
				attendats.add(map);	
				System.out.println(sid+"\t"+name+"\t"+price+"\t"+stock+"\t"+comm+"\t");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return attendats;
	}

	private Map<String, String> getOne (Integer SID){
		String  sid, name, price, stock, comm;	
		Map<String, String> map = new HashMap<String, String>();
		String query = ("SELECT * FROM Product WHERE Prd_ID=" + SID);
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("Prd_Name");
				price = rs.getString("Prd_Price");
				sid = rs.getString("Prd_ID");
				stock = rs.getString("Prd_Stock");
				comm = rs.getString("Prd_Comments");
				map.put("Name", name);
				map.put("Sid", sid);
				map.put("Price", price);
				map.put("Comment", comm);
				map.put("Stock", stock);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if (map.size() == 0) {
			map.put("Name", "");
			map.put("Sid", "0");
			map.put("Price", "");
			map.put("Comment", "");
			map.put("Stock", "");
		}
		return map;
	}

	private void deleteOne (Integer SID) {	
		String sql = "DELETE FROM Product WHERE Prd_ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, SID.toString());

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
			    System.out.println("A user was deleted successfully!");
			}
		}
		catch(Exception e) {
			System.err.println("Error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Name");
		String stock = request.getParameter("Stock");
		Integer sid = Integer.parseInt(request.getParameter("Sid"));
		String price = request.getParameter("Price");
		String comment = request.getParameter("Comment");
		String query ="";
		if (sid == 0) {
			query = ("INSERT INTO Product (Prd_Name, Prd_Stock, Prd_Price, Prd_Comments) VALUES (?, ?, ?, ?)");
		}
		else {
			query = ("UPDATE Product SET Prd_Name=?, Prd_Stock=?, Prd_Price=?, Prd_Comments=? WHERE Prd_ID=?");
		}
			
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, stock);
			statement.setString(3, price);
			statement.setString(4, comment);
			if (sid > 0) {
				statement.setString(5, sid.toString());
			}

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("Success!");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		response.sendRedirect("product");
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
