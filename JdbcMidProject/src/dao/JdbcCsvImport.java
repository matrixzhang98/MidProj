package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import service.EmpServGrantDaoImpl;
import dao.FileDownload;

public class JdbcCsvImport {
	
	public Connection conn;
	
	public void dataImport() throws Exception{
		
		JdbcConnUtil connUtil = new JdbcConnUtil();
		conn = connUtil.createConn();
		
		EmpServGrantDaoImpl esg = new EmpServGrantDaoImpl();
		
		File file = new File("c:/Temp/test.csv");
		
		try(BufferedReader br = new BufferedReader(new FileReader(file));
		PreparedStatement statement = conn.prepareStatement(esg.sqlstrInsert);){
			br.readLine();
			String line;
			while((line = br.readLine()) != null) {
				String[] data = line.split(",");
				for(int i=0;i<data.length;i++) {
					statement.setString(i+1,data[i]);
				}
				statement.addBatch();
			}
			statement.executeBatch();
		}catch(IOException | SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
	}

}
