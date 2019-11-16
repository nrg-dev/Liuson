package com.nrg.liusen.dao;

import java.util.List;

import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.RawMaterial;

/**
 * This Java Class will communicate with Database
 * @author Robert Arjun 
 * @date 24-11-2015
 * @copyright NRG      
 */
public interface EmployeeDao {

	List<String> getQualification();

	List<String> getDesignation();

	String inserEmployee(EmployeeDataBean employeeDataBean);

	List<String> getEmployeeID();

	public List<Employee> getemp(EmployeeDataBean employeeDataBean);

	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean);

	

	public List<Employee> getempedit(String empEmployeeName,
			EmployeeDataBean employeeDataBean);

	public String getupdate(EmployeeDataBean employeeDataBean);

	public String deleteemployee(EmployeeDataBean employeeDataBean);

	public List<String> empid();

	public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean);
	
	public String insertpayroll(EmployeeDataBean employeeDataBean);

	public String payroll(EmployeeDataBean employeeDataBean)throws LiusenException;

	public String payroll1(EmployeeDataBean employeeDataBean);

	public List<String> getEmployeeName();

	public List<com.nrg.liusen.domain.EmployeeDataBean> getPayrollList(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);

	public List<com.nrg.liusen.domain.EmployeeDataBean> getPayrollList1(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);

	public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean);

	public String payrollDelete(EmployeeDataBean employeeDataBean);

	public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean);

	public String editProduct(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);
	
	public String payrollremainder(EmployeeDataBean employeeDataBean);
}
