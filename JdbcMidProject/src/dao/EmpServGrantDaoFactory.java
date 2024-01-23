package dao;

import service.EmpServGrantDaoImpl;

public class EmpServGrantDaoFactory {
	public static IEmpServGrantDao createEmpServGrantDaoFactory() {
		return new EmpServGrantDaoImpl();
	}
}
