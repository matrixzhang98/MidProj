package service;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.IEmpServGrantDao;
import entity.EmpServGrant;

public class EmpServGrantDaoImpl implements IEmpServGrantDao {
	
	private Connection conn;
	public String sqlstrInsert = "Insert into ServGrant(grantName, business, phone,extensionNum) Values(?, ?, ?, ?)";
	public String sqlstrUpdate = "Update ServGrant set grantName = ?, phone = ? where id = ?";
	public String sqlstrDeleteById = "Delete from ServGrant where id = ?";
	public String sqlstrDeleteImages = "Delete from Gallery where galleryId = ?";
	public String sqlstrFindById = "Select * from ServGrant where id = ?";
	public String sqlstrFuzzy = "Select * from ServGrant where grantName like ?";
	public String sqlstrFuzzyDelete = "Delete ServGrant where grantName like ?";
	public String sqlstrImgRetrieve = "Select * from Gallery where galleryId = ?";
	public String sqlstrImgStore = "Insert into Gallery(imageName, imageFile) values(?, ?)";
	public String queryFileDataExist = "Select count(*) from ServGrant where grantName = ?";
	public String insertFileDataQuery = "Insert into ServGrant (grantName, business, phone,extensionNum) values (?, ?, ?, ?)";
	public String sqlurlstr = "jdbc:sqlserver://localhost:1433;databaseName=EmploymentServGrant;user=watcher;password=1234;encrypt=true;trustServerCertificate=true";
	
	@Override
	public void add(EmpServGrant g) throws SQLException {
		PreparedStatement state = conn.prepareStatement(sqlstrInsert);
		state.setString(1, g.getGrantName());
		state.setString(2, g.getBusiness());
		state.setString(3, g.getPhone());
		state.setString(4, g.getExtensionNum());
		int resultCount = state.executeUpdate();
		if(resultCount > 0) {
			System.out.println("Insert successfully.");
			System.out.println("resultCount: " + resultCount);
		}else {
			System.out.println("Insert failed.");
		}
		state.close();
	}

	@Override
	public void update(int id, String updatedGrantName, String updatedPhone) throws SQLException {
		PreparedStatement state = conn.prepareStatement(sqlstrUpdate);
		state.setString(1, updatedGrantName);
	    state.setString(2, updatedPhone);
	    state.setInt(3, id);
		int resultCount = state.executeUpdate();
		if(resultCount > 0) {
			System.out.println("Update successfully.");
			System.out.println("resultCount: " + resultCount);
		}else {
			System.out.println("Update failed.");
		}
		state.close();
	}

	@Override
	public void deleteById(int id) throws SQLException {
		PreparedStatement state = conn.prepareStatement(sqlstrDeleteById);
		state.setInt(1, id);
		int resultCount = state.executeUpdate();
		if(resultCount > 0) {
			System.out.println("Delete successfully.");
			System.out.println("resultCount: " + resultCount);
		}else {
			System.out.println("Delete failed.");
		}
		state.close();
	}
	
	@Override
	public void deleteImages(int id) throws SQLException {
		PreparedStatement state = conn.prepareStatement(sqlstrDeleteImages);
		state.setInt(1, id);
		int resultCount = state.executeUpdate();
		if(resultCount > 0) {
			System.out.println("Delete images successfully.");
			System.out.println("resultCount: " + resultCount);
		}else {
			System.out.println("Delete failed.");
		}
		state.close();
	}

	@Override
	public EmpServGrant findById(int id) throws SQLException {
	    PreparedStatement state = conn.prepareStatement(sqlstrFindById);//select all
	    state.setInt(1, id);
	    ResultSet rs = state.executeQuery();

	    EmpServGrant g = null;
	    
	    try(FileWriter writer = new FileWriter("c:/Temp/" + id + ".txt")){
	    	
	    	while (rs.next()) {
		        g = new EmpServGrant(
		                rs.getInt("id"),//column name
		                rs.getString("grantName"),
		                rs.getString("business"),
		                rs.getString("phone"),
		                rs.getString("extensionNum")
		        );
		    }
	    	
	    	if(g!=null) {
	    		writer.write("ID: " + g.getId() + " ");
		        writer.write("Grant Name: " + g.getGrantName() + " ");
		        writer.write("Business: " + g.getBusiness() + " ");
		        writer.write("Phone: " + g.getPhone() + " ");
		        writer.write("Extension Number: " + g.getExtensionNum() + " ");
//		        System.out.println("File export successfully.");
	    	}
	    	else {
	    		System.out.println("No data found for ID: " + id);
	    	}
            writer.flush();
            if (writer != null) {
                writer.close();
            }
	    } catch (IOException e) {
	        System.out.println("Error exporting data to file: " + e.getMessage());
	    }

	    rs.close();
	    state.close();	    
	    return g;
	}
	
	@Override
	public List<EmpServGrant> findByGrantName(String keywords) throws SQLException {//fuzzy search
	    List<EmpServGrant> resultList = new ArrayList<>();
	    
	    try (PreparedStatement state = conn.prepareStatement(sqlstrFuzzy)) {
	        state.setString(1, "%" + keywords + "%");
	        ResultSet rs = state.executeQuery();
	        int cnt = 0;
	        while (rs.next()) {
	            EmpServGrant g = new EmpServGrant(
	                    rs.getInt("id"),
	                    rs.getString("grantName"),
	                    rs.getString("business"),
	                    rs.getString("phone"),
	                    rs.getString("extensionNum")
	            );
	            resultList.add(g);
	            cnt++;
	        }
	        rs.close();
	        System.out.println("Find " + cnt + " rows.");
	    }
	    return resultList;
	}
	
	@Override
	public List<EmpServGrant> deleteByGrantName(String keywords) throws SQLException {//fuzzy delete
	    List<EmpServGrant> resultList = new ArrayList<>();
	    
	    try (PreparedStatement state = conn.prepareStatement(sqlstrFuzzyDelete)) {
	        state.setString(1, "%" + keywords + "%");
	        int affectedRows = state.executeUpdate();

	        System.out.println("Deleted " + affectedRows + " rows.");

	        // If you want to return something, you can modify this part
	        if (affectedRows > 0) {
	            resultList = findByGrantName(keywords);
	        }
	    }
	    return resultList;
	}
	
	
	public void ImageStore() throws SQLException, IOException{
		FileInputStream fis1 = new FileInputStream("c:/Temp/images01.jpg");
		PreparedStatement state = conn.prepareStatement(sqlstrImgStore);
		state.setString(1, "images01");
		state.setBinaryStream(2, fis1);
		state.executeUpdate();
		state.close();
		System.out.println("Images Stored.");
	}
	
	
	public void ImageRetrieve() throws SQLException, IOException{
		
		PreparedStatement state = conn.prepareStatement(sqlstrImgRetrieve);
		state.setInt(1, 1);
		ResultSet rs = state.executeQuery();
		
		if(rs.next()) {
			Blob blob = rs.getBlob(3);
			int length = (int)blob.length();
			System.out.println("length:" + length);
			
			BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream("c:/Temp/myImages01.jpg"));
			bos1.write(blob.getBytes(1, length));
			bos1.flush();
			bos1.close();
			
			rs.close();
			state.close();
		}
	}
	
	public void processAction() throws Exception { // images store and retrieve
		this.createConn();
		ImageStore();
		ImageRetrieve();
		this.closeConn();
	}

	@Override
	public void createConn() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn = DriverManager.getConnection(sqlurlstr);
		System.out.println("Connection Status:" + !conn.isClosed());
	}

	@Override
	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
		System.out.println("Connection Closed");
	}

}
