

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

public class Attendant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher = null;
	Connection connection = null;

	public Attendant () {
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
			request.setAttribute("attendants", getAll());
			dispatcher = request.getRequestDispatcher("/attendant/view.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("action").equals("update")) {
			request.setAttribute("attendant", getOne(Integer.parseInt(request.getParameter("id"))));
			dispatcher = request.getRequestDispatcher("/attendant/edit.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("action").equals("delete")) {
			deleteOne(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("attendant");
		} else {
			dispatcher = request.getRequestDispatcher("/attendant/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	private ArrayList<Map<String, String>> getAll () {
		String  sid, name, add, mob, comm;		
		ArrayList<Map<String, String>> attendats = new ArrayList<Map<String, String>>();
		String query = ("SELECT * FROM Attendant");
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("Att_Name");
				add = rs.getString("Att_Address");
				sid = rs.getString("Att_ID");
				mob = rs.getString("Att_MobileNum");
				comm = rs.getString("Att_Comments");				
				Map<String, String> map = new HashMap<String, String>();
				map.put("Name", name);
				map.put("Sid", sid);
				map.put("Mobile", mob);
				map.put("Comment", comm);
				map.put("Address", add);
				attendats.add(map);	
				System.out.println(sid+"\t"+name+"\t"+add+"\t"+mob+"\t"+comm+"\t");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return attendats;
	}

	private Map<String, String> getOne (Integer SID) {
		String  sid = SID.toString(), name = "", add = "", mob = "", comm = "";		
		Map<String, String> map = new HashMap<String, String>();
		String query = ("SELECT * FROM Attendant WHERE Att_ID=" + SID);
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("Att_Name");
				add = rs.getString("Att_Address");
				sid = rs.getString("Att_ID");
				mob = rs.getString("Att_MobileNum");
				comm = rs.getString("Att_Comments");
				map.put("Name", name);
				map.put("Sid", sid);
				map.put("Mobile", mob);
				map.put("Comment", comm);
				map.put("Address", add);
				System.out.println(sid+"\t"+name+"\t"+add+"\t"+mob+"\t"+comm+"\t");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if(map.size() == 0) {
			map.put("Name", "");
			map.put("Sid", "0");
			map.put("Mobile", "");
			map.put("Comment", "");
			map.put("Address", "");
		}
		return map;
	}

	private void deleteOne (Integer SID) {	
		String sql = "DELETE FROM Attendant WHERE Att_ID=?";
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
		String mobile = request.getParameter("Mobile");
		Integer sid = Integer.parseInt(request.getParameter("Sid"));
		String address = request.getParameter("Address");
		String comment = request.getParameter("Comment");
		String query ="";
		if (sid == 0) {
			query = ("INSERT INTO Attendant (Att_Name,Att_Address, Att_MobileNum, Att_Comments) VALUES (?, ?, ?, ?)");
		}
		else {
			query = ("UPDATE Attendant SET Att_Name=?, Att_Address=?, Att_MobileNum=?, Att_Comments=? WHERE Att_ID=?");
		}
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, address);
			statement.setString(3, mobile);
			statement.setString(4, comment);
			if (sid > 0) {
				statement.setString(5, sid.toString());
			}

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("Success!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("attendant");
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
