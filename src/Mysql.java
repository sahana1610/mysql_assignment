import java.sql.*;
import java.util.Scanner;
 
public class Mysql {

	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	   static final String DB_URL = "jdbc:mysql://localhost/Assignmentdb?autoReconnect=true&useSSL=false";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   static ResultSet rs = null;
	   static Connection conn = null;
	   Statement stmt = null;
	   static PreparedStatement statement = null;
	   static String sql;

	   @SuppressWarnings("resource")
	public static void main(String[] args) {
	   
	   try{
	      //STEP 1: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 2: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	     
	      //STEP 3: Insert into table	    
	      sql = "INSERT INTO userdetails(id, name, email, mobile,city,user_status) VALUES (?, ?, ?, ?, ?, ?)";
	      
	      statement = conn.prepareStatement(sql);
	      statement.setString(1, null);
	      statement.setString(2, "Soumya");
	      statement.setString(3, "soumya123@gmail.com");
	      statement.setInt(4, 1313142861);
	      statement.setString(5, "Bangalore");
	      statement.setBoolean(6, true);
	      
	      try {
	    	  int rowsInserted = statement.executeUpdate();
	    	  if (rowsInserted > 0) {
	    		    System.out.println("A new user inserted successfully!");
	    	  }
	      }catch(SQLIntegrityConstraintViolationException si) {
	    	  System.out.println("Failed to Insert user,Duplicate Found!");
	      }
	      display();
	    
	      //STEP 4: Update
	      updateUser();
	      
	      //STEP 5: Delete
	      deleteUser();
	      
	      //STEP 6: Clean-up environment
	      rs.close();
	      statement.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(statement!=null)
	            statement.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Thank you!");
	}//end main

	private static void deleteUser() {
		sql = "DELETE FROM userdetails WHERE id=? OR user_status = false";
		
		System.out.println("Enter Id to delete:");
		Scanner in = new Scanner(System.in);
		int id = in.nextInt();
	      
	      try {
	    	  statement = conn.prepareStatement(sql);
	    	  statement.setInt(1, id);
	       
		      int rowsDeleted = statement.executeUpdate();
		      if (rowsDeleted > 0) {
		          System.out.println("A user deleted successfully!");
		      }
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	      display();		
	}


	private static void updateUser() {
		 sql = "UPDATE userdetails SET city = ? WHERE id=?";
		 System.out.println("Enter Id to update:");
		 Scanner in1 = new Scanner(System.in);
		 int id = in1.nextInt();
			
		 System.out.println("Enter CityName:");
		 Scanner in = new Scanner(System.in);
		 String cityName = in.nextLine();
		 
	     try {
	    	  statement = conn.prepareStatement(sql);
	    	  statement.setString(1, cityName);
	    	  statement.setInt(2, id);
		      int rowsUpdated = statement.executeUpdate();
		      if (rowsUpdated > 0) {
		          System.out.println("An existing user was updated successfully!");
		      }
	      }catch(SQLException se) {
	    	  System.out.println("Failed to Update Data!");
	    	  se.printStackTrace();
	      }
	      display();
		
	}

	private static void display() {
		  sql = "SELECT * FROM userdetails";
	      try {
			rs = statement.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         String name = rs.getString("name");
	         String email = rs.getString("email");
	         int mobile = rs.getInt("mobile");
	         String city = rs.getString("city");
	         Boolean user_status = rs.getBoolean("user_status");
	         
	         //Display values
	         System.out.print("ID: " + id + ", ");
	         System.out.print("NAME: " + name + ", ");
	         System.out.print("EMAIL: " + email + ", ");
	         System.out.print("MOBILE: " + mobile + ", ");
	         System.out.print("CITY: " + city + ", ");
	         System.out.println("USER_STATUS: " + user_status);

	      }
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}


}//end FirstExample
