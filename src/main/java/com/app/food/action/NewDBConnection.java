package com.app.food.action;
import java.sql.Connection;
import java.sql.DriverManager;

public class NewDBConnection {

  public static Connection connection = null;

  static String dbUrl =
			"jdbc:mysql://localhost/foods?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
  
  // static String dbUrl =
  // "jdbc:mysql://162.150.26.169/cir?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";

  // static String dbUrl =
  // "jdbc:mysql://localhost/pacebcmxg2v2g?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";

  public static void main(String args[]) {

    NewDBConnection.getConenction();
  }
  public static Connection getConenction() {
    
    if(connection == null) {
      
      try {
        
        Class.forName("com.mysql.jdbc.Driver");
        
        connection =
						DriverManager.getConnection(dbUrl, "root", "");
        
        return connection;
        
      } catch (Exception e) {
        e.printStackTrace();
      }
  
    } else {
      
      return connection;
    }
    
    return null;
  }

}
