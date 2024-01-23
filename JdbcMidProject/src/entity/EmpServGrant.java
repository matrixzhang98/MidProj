package entity;

import java.io.Serializable;

//JavaBean
public class EmpServGrant implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String grantName;
	private String business;
	private String phone;
	private String extensionNum;
	
	public EmpServGrant() {
		
	}

	public EmpServGrant(String grantName, String business, String phone, String extensionNum) {
		super();
		this.grantName = grantName;
		this.business = business;
		this.phone = phone;
		this.extensionNum = extensionNum;
	}
	
	public EmpServGrant(int id, String grantName, String business, String phone, String extensionNum) {
		super();
		this.id = id;
		this.grantName = grantName;
		this.business = business;
		this.phone = phone;
		this.extensionNum = extensionNum;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGrantName() {
		return grantName;
	}
	
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	
	public String getBusiness() {
		return business;
	}
	
	public void setBusiness(String business) {
		this.business = business;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getExtensionNum() {
		return extensionNum;
	}
	
	public void setExtensionNum(String extensionNum) {
		this.extensionNum = extensionNum;
	}

	@Override
	public String toString() {
		return "EmpServGrant [id in java = " + id + ", grantName = " + grantName + ", business = " + business + ", phone = " + phone
				+ ", extensionNum = " + extensionNum + "]";
	}
	
}
