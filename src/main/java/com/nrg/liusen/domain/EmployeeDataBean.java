package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;

import com.nrg.liusen.shared.Designation;

public class EmployeeDataBean {

	private String empEmployeeName;
	private String empEmployeeId;
	private Date empDob;
	private String empMaildId;
	private String empGender;
	private String empPhoneNo;
	private String empAddress;
	private String empQualification;
	private Date empEntryDate;
	private Date empJoinDate;
	private String empBasicSalary;
	private String empDesignation;
	private String empDescription;
	private Date empPayTodayDate;
	private Date empPayMonth;
	private String empPayYear;
	private String empPayWorkDays;
	private String empPayCommision;
	private String empPayOTAmmount;
	private String empPayLoanAdvance;
	private String empPayTotalSalary;
	private String empPayMonthDate;
	private String empPayempName;
	private String empPayempId;
	private String validmonth;
	private List<EmployeeDataBean> payrollList=null; 
	private List<EmployeeDataBean> payrollList1=null; 
	public List<EmployeeDataBean> getPayrollList1() {
		return payrollList1;
	}
	public void setPayrollList1(List<EmployeeDataBean> payrollList1) {
		this.payrollList1 = payrollList1;
	}
	public List<EmployeeDataBean> getPayrollList() {
		return payrollList;
	}
	public void setPayrollList(List<EmployeeDataBean> payrollList) {
		this.payrollList = payrollList;
	}
	/**
	 * @return the empPayempName
	 */
	
	public String getEmpPayempName() {
		return empPayempName;
	}
	public String getValidmonth() {
		return validmonth;
	}
	public void setValidmonth(String validmonth) {
		this.validmonth = validmonth;
	}
	/**
	 * @param empPayempName the empPayempName to set
	 */
	public void setEmpPayempName(String empPayempName) {
		this.empPayempName = empPayempName;
	}
	/**
	 * @return the empPayempId
	 */
	public String getEmpPayempId() {
		return empPayempId;
	}
	/**
	 * @param empPayempId the empPayempId to set
	 */
	public void setEmpPayempId(String empPayempId) {
		this.empPayempId = empPayempId;
	}
	/**
	 * @return the empPayMonthDate
	 */
	public String getEmpPayMonthDate() {
		return empPayMonthDate;
	}
	/**
	 * @param empPayMonthDate the empPayMonthDate to set
	 */
	public void setEmpPayMonthDate(String empPayMonthDate) {
		this.empPayMonthDate = empPayMonthDate;
	}
	/**
	 * @return the empPayTodayDate
	 */
	public Date getEmpPayTodayDate() {
		return empPayTodayDate;
	}
	/**
	 * @param empPayTodayDate the empPayTodayDate to set
	 */
	public void setEmpPayTodayDate(Date empPayTodayDate) {
		this.empPayTodayDate = empPayTodayDate;
	}
	/**
	 * @return the empPayMonth
	 */
	
	/**
	 * @return the empPayYear
	 */
	public String getEmpPayYear() {
		return empPayYear;
	}
	/**
	 * @return the empPayMonth
	 */
	public Date getEmpPayMonth() {
		return empPayMonth;
	}
	/**
	 * @param empPayMonth the empPayMonth to set
	 */
	public void setEmpPayMonth(Date empPayMonth) {
		this.empPayMonth = empPayMonth;
	}
	/**
	 * @param empPayYear the empPayYear to set
	 */
	public void setEmpPayYear(String empPayYear) {
		this.empPayYear = empPayYear;
	}
	/**
	 * @return the empPayWorkDays
	 */
	public String getEmpPayWorkDays() {
		return empPayWorkDays;
	}
	/**
	 * @param empPayWorkDays the empPayWorkDays to set
	 */
	public void setEmpPayWorkDays(String empPayWorkDays) {
		this.empPayWorkDays = empPayWorkDays;
	}
	/**
	 * @return the empPayCommision
	 */
	public String getEmpPayCommision() {
		return empPayCommision;
	}
	/**
	 * @param empPayCommision the empPayCommision to set
	 */
	public void setEmpPayCommision(String empPayCommision) {
		this.empPayCommision = empPayCommision;
	}
	/**
	 * @return the empPayOTAmmount
	 */
	public String getEmpPayOTAmmount() {
		return empPayOTAmmount;
	}
	/**
	 * @param empPayOTAmmount the empPayOTAmmount to set
	 */
	public void setEmpPayOTAmmount(String empPayOTAmmount) {
		this.empPayOTAmmount = empPayOTAmmount;
	}
	/**
	 * @return the empPayLoanAdvance
	 */
	public String getEmpPayLoanAdvance() {
		return empPayLoanAdvance;
	}
	/**
	 * @param empPayLoanAdvance the empPayLoanAdvance to set
	 */
	public void setEmpPayLoanAdvance(String empPayLoanAdvance) {
		this.empPayLoanAdvance = empPayLoanAdvance;
	}
	/**
	 * @return the empPayTotalSalary
	 */
	public String getEmpPayTotalSalary() {
		return empPayTotalSalary;
	}
	/**
	 * @param empPayTotalSalary the empPayTotalSalary to set
	 */
	public void setEmpPayTotalSalary(String empPayTotalSalary) {
		this.empPayTotalSalary = empPayTotalSalary;
	}
	/**
	 * @return the empEmployeeName
	 */
	public String getEmpEmployeeName() {
		return empEmployeeName;
	}
	/**
	 * @param empEmployeeName the empEmployeeName to set
	 */
	public void setEmpEmployeeName(String empEmployeeName) {
		this.empEmployeeName = empEmployeeName;
	}
	/**
	 * @return the empEmployeeId
	 */
	public String getEmpEmployeeId() {
		return empEmployeeId;
	}
	/**
	 * @param empEmployeeId the empEmployeeId to set
	 */
	public void setEmpEmployeeId(String empEmployeeId) {
		this.empEmployeeId = empEmployeeId;
	}
	/**
	 * @return the empDob
	 */
	public Date getEmpDob() {
		return empDob;
	}
	/**
	 * @param empDob the empDob to set
	 */
	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}
	/**
	 * @return the empMaildId
	 */
	public String getEmpMaildId() {
		return empMaildId;
	}
	/**
	 * @param empMaildId the empMaildId to set
	 */
	public void setEmpMaildId(String empMaildId) {
		this.empMaildId = empMaildId;
	}
	/**
	 * @return the empGender
	 */
	public String getEmpGender() {
		return empGender;
	}
	/**
	 * @param empGender the empGender to set
	 */
	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}
	/**
	 * @return the empPhoneNo
	 */
	public String getEmpPhoneNo() {
		return empPhoneNo;
	}
	/**
	 * @param empPhoneNo the empPhoneNo to set
	 */
	public void setEmpPhoneNo(String empPhoneNo) {
		this.empPhoneNo = empPhoneNo;
	}
	/**
	 * @return the empAddress
	 */
	public String getEmpAddress() {
		return empAddress;
	}
	/**
	 * @param empAddress the empAddress to set
	 */
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	/**
	 * @return the empQualification
	 */
	public String getEmpQualification() {
		return empQualification;
	}
	/**
	 * @param empQualification the empQualification to set
	 */
	public void setEmpQualification(String empQualification) {
		this.empQualification = empQualification;
	}
	/**
	 * @return the empEntryDate
	 */
	public Date getEmpEntryDate() {
		return empEntryDate;
	}
	/**
	 * @param empEntryDate the empEntryDate to set
	 */
	public void setEmpEntryDate(Date empEntryDate) {
		this.empEntryDate = empEntryDate;
	}
	/**
	 * @return the empJoinDate
	 */
	public Date getEmpJoinDate() {
		return empJoinDate;
	}
	/**
	 * @param empJoinDate the empJoinDate to set
	 */
	public void setEmpJoinDate(Date empJoinDate) {
		this.empJoinDate = empJoinDate;
	}
	/**
	 * @return the empBasicSalary
	 */
	public String getEmpBasicSalary() {
		return empBasicSalary;
	}
	/**
	 * @param empBasicSalary the empBasicSalary to set
	 */
	public void setEmpBasicSalary(String empBasicSalary) {
		this.empBasicSalary = empBasicSalary;
	}
	/**
	 * @return the empDesignation
	 */
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	/**
	 * @param designation the empDesignation to set
	 */
	
	/**
	 * @return the empDescription
	 */
	public String getEmpDescription() {
		return empDescription;
	}
	/**
	 * @param empDescription the empDescription to set
	 */
	public void setEmpDescription(String empDescription) {
		this.empDescription = empDescription;
	}
	public void setEmpDesignation(Designation designation) {
		// TODO Auto-generated method stub
		this.empDesignation=empDesignation;
	}
	public int payrollMM;
	public int payrolldirector;
	public int getPayrollMM() {
		return payrollMM;
	}
	public void setPayrollMM(int payrollMM) {
		this.payrollMM = payrollMM;
	}
	public int getPayrolldirector() {
		return payrolldirector;
	}
	public void setPayrolldirector(int payrolldirector) {
		this.payrolldirector = payrolldirector;
	}
	public Date fromDate;
	public Date toDate;
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}	
}
