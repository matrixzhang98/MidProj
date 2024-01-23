package controller;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import dao.EmpServGrantDaoFactory;
import dao.FileDownload;
import dao.IEmpServGrantDao;
import dao.JdbcConnUtil;
import dao.JdbcCsvImport;
import entity.EmpServGrant;
import service.EmpServGrantDaoImpl;

public class EmpServGrantDaoAction {

	private static Connection conn;

	public static void main(String[] args) throws Exception {
		
		IEmpServGrantDao gDao = EmpServGrantDaoFactory.createEmpServGrantDaoFactory();
		JdbcConnUtil connUtil = new JdbcConnUtil();
		conn = connUtil.createConn();		
		gDao.createConn();
		
		try {
			FileDownload.UrlDealDownload();
            // import csv file to DB
            JdbcCsvImport csvImport = new JdbcCsvImport();
            csvImport.dataImport();
		}catch (Exception e) {
			e.printStackTrace();
            System.err.println("Something went wrong: " + e.getMessage());
		}
		
		try(Scanner scanner = new Scanner(System.in);){
			boolean isRunning = true;
			while(isRunning) {
				System.out.println("Enter choice: ");
				System.out.println("1. Insert");
				System.out.println("2. Select all and export file to txt");
				System.out.println("3. Fuzzy search");
				System.out.println("4. Delete by ID");
				System.out.println("5. Fuzzy delete");
				System.out.println("6. Update");
				System.out.println("7. Picture access");
				System.out.println("8. Picture delete");
				System.out.println("9. Picture delete on disk");
				System.out.println("10. Exit");
				int choice = Integer.parseInt(scanner.nextLine());
				
				switch(choice) {
				case 1 -> { // insert
					System.out.println("Insert grant Name, business, phone ,extension number: ");
					EmpServGrant gToAdd = new EmpServGrant(scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine());
					gDao.add(gToAdd);
					System.out.println(gToAdd);
				}
				case 2 -> {// find by id and export result to file.txt
					System.out.println("Enter the data ID that you want to search: ");
					int toFind = Integer.parseInt(scanner.nextLine());
					System.out.println(gDao.findById(toFind));
				}
				case 3 -> {// fuzzy search
					System.out.print("Enter keyword for fuzzy search in grant name: ");
				    String keywordSearch = scanner.nextLine();
				    List<EmpServGrant> resultListSearch = gDao.findByGrantName(keywordSearch);

				    if (resultListSearch.isEmpty()) {
				        System.out.println("No matching records found.");
				    } else {
				    	resultListSearch.forEach(System.out::println);
				    }
				}
				case 4 -> { // delete
					System.out.print("Enter the data ID that you want to delete: ");
					int toDelete = Integer.parseInt(scanner.nextLine());
					System.out.println("You delete the data in DataBase: " + gDao.findById(toDelete));
					gDao.deleteById(toDelete);
				}
				case 5 -> { // fuzzy delete
					System.out.print("Enter keyword for fuzzy delete in grant name: ");
				    List<EmpServGrant> resultListDelete = gDao.deleteByGrantName(scanner.nextLine());

				    if (resultListDelete.isEmpty()) {
				        System.out.println("Fuzzy delete successfully.");
				    } else {
				        resultListDelete.forEach(System.out::println);
				    }
				}
				case 6 -> { // update
					System.out.print("Enter the ID of the data you want to update: ");
				    int updateId = Integer.parseInt(scanner.nextLine());
				    
				    // Retrieve the existing data
				    EmpServGrant existingData = gDao.findById(updateId);
				    
				    if (existingData != null) {
				        System.out.println("Existing Data: " + existingData);
				        System.out.println("Enter the updated grant Name, phone: ");
				        gDao.update(updateId, scanner.nextLine(), scanner.nextLine());
				        System.out.println("Updated Data: " + gDao.findById(updateId));
				    } else {
				        System.out.println("Data with ID " + updateId + " not found.");
				    }
				}
				case 7 -> { // picture access
					new EmpServGrantDaoImpl().processAction();
				}
				case 8 -> { // picture delete
					System.out.print("Enter the picture ID that you want to delete: ");
					int toDelete = Integer.parseInt(scanner.nextLine());
					System.out.println("You delete the picture in DataBase: " + gDao.findById(toDelete));
					gDao.deleteImages(toDelete);
				}
				case 9 -> { // picture delete on disk
					File file = new File("c:/Temp/myImages01.jpg");
			        if(file.delete()){
			            System.out.println("Images is deleted on disk.");
			        }else System.out.println("Images doesn't exist.");
				}
				case 10 -> { // exit
					System.out.println("Thank you and hope you visit again. Bye Bye!");
					isRunning = false;
				}
				default -> System.out.println("Incorrect choice."); // incorrect input
				}
			}
		}catch(Exception e) {
			throw new RuntimeException("Something went wrong..." + e);
		}finally {
			gDao.closeConn();
			connUtil.closeConn();
		}
	}
}
