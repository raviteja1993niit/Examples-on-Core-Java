package com.psx.prime360ClientService.serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class WtoF {
	public static void main(String[] args) {
		WtoF w = new WtoF();

		try {
			w.viewAllCounters();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void viewAllCounters() throws SQLException {
		Statement stmt = null;
		String query = "select AREA,AREA_ID,AREA_UNQ_ID,BATCHID,CITY,DUIFLAG,EVENTTYPE,INSERT_TIME,LCHGTIME,ORG,PIN,PSX_AREA,PSX_BATCH_ID,SEGMENT,STATE from PSX_NEGATIVEAREA_BASE";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.148:1524:pdborcl",
					"bajaj_migration2", "posidex");
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				// File file = new File("BFL-" + "PSX_NEGATIVEAREA_BASE" + ".csv");
				String destinationPath = "/home/raviteja/RaviTeja/NegativeFileDownload/BFL-PSX_NEGATIVEAREA_BASE.csv";
				File file = new File(destinationPath);
				// AREA_UNQ_ID|AREA_ID|AREA|CITY|PIN|STATE|SEGMENT|ORG
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.append("AREA_UNQ_ID");
				fileWriter.append('|');
				fileWriter.append("AREA_ID");
				fileWriter.append('|');
				fileWriter.append("AREA");
				fileWriter.append('|');
				// fileWriter.append("BATCHID");
				// fileWriter.append('|');
				fileWriter.append("CITY");
				fileWriter.append('|');
				fileWriter.append("PIN");
				fileWriter.append('|');
				fileWriter.append("STATE");
				fileWriter.append('|');
				// fileWriter.append("DUIFLAG");
				// fileWriter.append('|');
				// fileWriter.append("EVENTTYPE");
				// fileWriter.append('|');

				/*
				 * fileWriter.append("INSERT_TIME"); fileWriter.append('|');
				 * fileWriter.append("LCHGTIME"); fileWriter.append('|');
				 */

				fileWriter.append("SEGMENT");
				fileWriter.append('|');
				fileWriter.append("ORG");
				// fileWriter.append('|');

				/*
				 * fileWriter.append("PSX_AREA"); fileWriter.append('|');
				 * fileWriter.append("PSX_BATCH_ID");
				 */

				fileWriter.append('\n');

				while (rs.next()) {
					String area = rs.getString("AREA");
					String area_id = rs.getString("AREA_ID");
					String area_unq_id = rs.getString("AREA_UNQ_ID");
					String city = rs.getString("CITY");
					String eventType = rs.getString("EVENTTYPE");
					String org = rs.getString("ORG");
					String pin = rs.getString("PIN");
					String segment = rs.getString("SEGMENT");
					String state = rs.getString("STATE");
					String batchid = rs.getString("BATCHID");
					String duiFlag = rs.getString("DUIFLAG");
					String insertTime = rs.getTimestamp("INSERT_TIME") + "";
					String lchgTime = rs.getTimestamp("LCHGTIME") + "";
					String psx_area = rs.getString("PSX_AREA");
					String psx_batchid = rs.getString("PSX_BATCH_ID");
					fileWriter.append(area_unq_id);
					fileWriter.append('|');
					fileWriter.append(area_id);
					fileWriter.append('|');
					fileWriter.append(area);
					fileWriter.append('|');
					fileWriter.append(city);
					fileWriter.append('|');
					fileWriter.append(pin);
					fileWriter.append('|');
					fileWriter.append(state);
					fileWriter.append('|');
					fileWriter.append(segment);
					fileWriter.append('|');
					fileWriter.append(org);
					/*
					 * fileWriter.append('|'); fileWriter.append(batchid); fileWriter.append('|');
					 * 
					 * fileWriter.append(duiFlag); fileWriter.append('|');
					 * fileWriter.append(eventType); fileWriter.append('|');
					 * fileWriter.append(insertTime); fileWriter.append('|');
					 * fileWriter.append(lchgTime); fileWriter.append('|');
					 * fileWriter.append(psx_area); fileWriter.append('|');
					 * fileWriter.append(psx_batchid); fileWriter.append('|');
					 */

					fileWriter.append('\n');
				}
				fileWriter.flush();
				fileWriter.close();
				System.out.println("File Written Successfully........");
			} catch (

			IOException e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage() + "\n\n\n");
			}
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() + "\n\n\n");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
