package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import entity.EmpServGrant;

public interface IEmpServGrantDao { //Encapsulation
	public void add(EmpServGrant g)throws SQLException;
	public void update(int id, String updatedGrantName, String updatedPhone) throws SQLException;
	public void deleteById(int id) throws SQLException;
	public void deleteImages(int id) throws SQLException;
	EmpServGrant findById(int id) throws SQLException;
	public List<EmpServGrant> findByGrantName(String keyword) throws SQLException;//fuzzy search
	public List<EmpServGrant> deleteByGrantName(String keywords) throws SQLException;//fuzzy delete
	public void ImageStore() throws SQLException, IOException;
	public void ImageRetrieve() throws SQLException, IOException;
	public void createConn() throws Exception; 
	public void closeConn() throws SQLException;
}
