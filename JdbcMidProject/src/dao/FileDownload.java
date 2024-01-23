package dao;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import service.EmpServGrantDaoImpl;

public class FileDownload {
	
	private static void downloadUsingStream(String urlStr, String file) throws Exception{
		EmpServGrantDaoImpl esg = new EmpServGrantDaoImpl();
        Connection conn = DriverManager.getConnection(esg.sqlurlstr);
		
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1){
        	//String data = new String(buffer, 0, count).replace("\"", "");
        	String line = new String(buffer, 0, count).replace("\"", "");
            String[] data = line.split(","); 
        	
        	String grantName = data[0];
//            byte[] processedData = data.getBytes();
        	byte[] processedData = line.getBytes();
            fos.write(processedData, 0, processedData.length);
            
//            
        }       
        fos.close();
        bis.close();
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    
    public static void UrlDealDownload() throws Exception{
    	String url = "https://odws.hccg.gov.tw/001/Upload/25/opendataback/9059/1507/63c5e028-f708-4037-91a2-d96d850b0c45.csv";

        try {
            downloadUsingNIO(url, "c:/Temp/test.csv");
            downloadUsingStream(url, "c:/Temp/test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
