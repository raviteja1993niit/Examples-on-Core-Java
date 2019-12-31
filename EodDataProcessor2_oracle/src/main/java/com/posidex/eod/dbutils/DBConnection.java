package com.posidex.eod.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class DBConnection {
	private static Logger logger=Logger.getLogger(DBConnection.class.getName());
	
		
	public Connection getConnection(Properties props,BasicDataSource dataSource) throws ClassNotFoundException, SQLException  {
		 //   logger.info("DB Properties.......... "+props);
		   /* String drivername= (String) dataSource.getDriverClassName();
	        String url= (String) dataSource.getUrl();
	        String username= (String) dataSource.getUsername();
	        String password= (String) dataSource.getPassword();*/
			
			String drivername = (String) props.get("database.driverClassName");
	        String url = (String) props.get("database.url");
	        String username = (String) props.get("database.username");
	        String password = (String) props.get("database.password");
			int maxConnections=Integer.parseInt((String)props.get("maxConnections"));
			int initialConnections=Integer.parseInt((String)props.get("initialConnections"));
			Boolean waitIfBusy=Boolean.valueOf(props.get("waitIfBusy")+"");
	       Connection con = null;
	       try {
	    	   System.out.println("ConnectionPool");
			 /*Class.forName("oracle.jdbc.driver.OracleDriver");
			 con = DriverManager.getConnection( "jdbc:oracle:thin:@192.168.1.148:1524:pdborcl", "bajaj_multibase", "psx123");*/
	    	   
	    	   
	    	   Class.forName(drivername);
				 con = DriverManager.getConnection( url, username, password);
				 con.setAutoCommit(false);
	    	    /* ConnectionPool connectionPool = new ConnectionPool( drivername,
	    	    		 url, username, password,
	    	    		 initialConnections, maxConnections, waitIfBusy);
	 
	            con = connectionPool.getConnection();*/
	    	   
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} 
	        logger.info("Returning  connection Object");
	       return con;
	     		
	}
	
		
	

}
