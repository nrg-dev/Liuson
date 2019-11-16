package com.nrg.liusen.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockOutManualDataBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String soutOrederNo;
	private String soutProjectName;
	private String soutLimit;
	private String soutQty;
	private String soutUnitPrice;
	private String soutProductName;
	private String soutNetAmount;
	private String soutTaxType;
	private String soutCrossTotal;
	private String soutTotalAmount;
	private String soutEmployeeID;
	private String soutServiceName;
	ArrayList<StockOutManualDataBean> stockOutList=new ArrayList<StockOutManualDataBean>();	
	private String soutSerialNo;
	private String soutProductName1;
	private List<String> projectList=null;
	private List<String> employeelist=null;	
	private List<String> customerlist=null;	
	private String flag="none";
	private String flag1="none";
	private String pflag="none";
	private String pflag1="none";
	private String qflag="none";
	private String qflag1="none";	
	private String message;	
	private String message1;	
	private int stockoutid;
	private Date soutDate;
	private Date fdate;
	private Date tdate;
	ArrayList<StockOutManualDataBean> stockOutList1=new ArrayList<StockOutManualDataBean>();
	private String soutProjectname;
	private String soutProjectnames;
	private String reason;
	private String action;
	private String status;
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the soutProjectname
	 */
	public String getSoutProjectname() {
		return soutProjectname;
	}

	/**
	 * @param soutProjectname the soutProjectname to set
	 */
	public void setSoutProjectname(String soutProjectname) {
		this.soutProjectname = soutProjectname;
	}

	public ArrayList<StockOutManualDataBean> getStockOutList1() {
		return stockOutList1;
	}

	public void setStockOutList1(ArrayList<StockOutManualDataBean> stockOutList1) {
		this.stockOutList1 = stockOutList1;
	}

	public Date getFdate() {
		return fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}

	public Date getTdate() {
		return tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public Date getSoutDate() {
		return soutDate;
	}

	public void setSoutDate(Date soutDate) {
		this.soutDate = soutDate;
	}

	public int getStockoutid() {
		return stockoutid;
	}

	public void setStockoutid(int stockoutid) {
		this.stockoutid = stockoutid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getPflag() {
		return pflag;
	}

	public void setPflag(String pflag) {
		this.pflag = pflag;
	}

	public String getPflag1() {
		return pflag1;
	}

	public void setPflag1(String pflag1) {
		this.pflag1 = pflag1;
	}

	public String getQflag() {
		return qflag;
	}

	public void setQflag(String qflag) {
		this.qflag = qflag;
	}

	public String getQflag1() {
		return qflag1;
	}

	public void setQflag1(String qflag1) {
		this.qflag1 = qflag1;
	}

	public List<String> getEmployeelist() {
		return employeelist;
	}
	
	public void setEmployeelist(List<String> employeelist) {
		this.employeelist = employeelist;
	}

	public List<String> getCustomerlist() {
		return customerlist;
	}

	public void setCustomerlist(List<String> customerlist) {
		this.customerlist = customerlist;
	}

	public List<String> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}
	
	/**
	 * @return the soutProductName1
	 */
	public String getSoutProductName1() {
		return soutProductName1;
	}
	/**
	 * @param soutProductName1 the soutProductName1 to set
	 */
	public void setSoutProductName1(String soutProductName1) {
		this.soutProductName1 = soutProductName1;
	}
	/**
	 * @return the soutSerialNo
	 */
	public String getSoutSerialNo() {
		return soutSerialNo;
	}
	/**
	 * @param soutSerialNo the soutSerialNo to set
	 */
	public void setSoutSerialNo(String soutSerialNo) {
		this.soutSerialNo = soutSerialNo;
	}
	/**
	 * @return the stockOutList
	 */
	public ArrayList<StockOutManualDataBean> getStockOutList() {
		return stockOutList;
	}
	/**
	 * @param stockOutList the stockOutList to set
	 */
	public void setStockOutList(ArrayList<StockOutManualDataBean> stockOutList) {
		this.stockOutList = stockOutList;
	}
	/**
	 * @return the soutServiceName
	 */
	public String getSoutServiceName() {
		return soutServiceName;
	}
	/**
	 * @param soutServiceName the soutServiceName to set
	 */
	public void setSoutServiceName(String soutServiceName) {
		this.soutServiceName = soutServiceName;
	}
	/**
	 * @return the soutEmployeeID
	 */
	public String getSoutEmployeeID() {
		return soutEmployeeID;
	}
	/**
	 * @param soutEmployeeID the soutEmployeeID to set
	 */
	public void setSoutEmployeeID(String soutEmployeeID) {
		this.soutEmployeeID = soutEmployeeID;
	}
	/**
	 * @return the soutCustomerName
	 */
	public String getSoutCustomerName() {
		return soutCustomerName;
	}
	/**
	 * @param soutCustomerName the soutCustomerName to set
	 */
	public void setSoutCustomerName(String soutCustomerName) {
		this.soutCustomerName = soutCustomerName;
	}
	private String soutCategory;
	private String soutCustomerName;
	
	
	/**
	 * @return the soutCategory
	 */
	public String getSoutCategory() {
		return soutCategory;
	}
	/**
	 * @param soutCategory the soutCategory to set
	 */
	public void setSoutCategory(String soutCategory) {
		this.soutCategory = soutCategory;
	}
	/**
	 * @return the soutOrederNo
	 */
	public String getSoutOrederNo() {
		return soutOrederNo;
	}
	/**
	 * @param soutOrederNo the soutOrederNo to set
	 */
	public void setSoutOrederNo(String soutOrederNo) {
		this.soutOrederNo = soutOrederNo;
	}
	/**
	 * @return the soutProjectName
	 */
	public String getSoutProjectName() {
		return soutProjectName;
	}
	/**
	 * @param soutProjectName the soutProjectName to set
	 */
	public void setSoutProjectName(String soutProjectName) {
		this.soutProjectName = soutProjectName;
	}
	/**
	 * @return the soutLimit
	 */
	public String getSoutLimit() {
		return soutLimit;
	}
	/**
	 * @param soutLimit the soutLimit to set
	 */
	public void setSoutLimit(String soutLimit) {
		this.soutLimit = soutLimit;
	}
	/**
	 * @return the soutQty
	 */
	public String getSoutQty() {
		return soutQty;
	}
	/**
	 * @param soutQty the soutQty to set
	 */
	public void setSoutQty(String soutQty) {
		this.soutQty = soutQty;
	}
	/**
	 * @return the soutUnitPrice
	 */
	public String getSoutUnitPrice() {
		return soutUnitPrice;
	}
	/**
	 * @param soutUnitPrice the soutUnitPrice to set
	 */
	public void setSoutUnitPrice(String soutUnitPrice) {
		this.soutUnitPrice = soutUnitPrice;
	}
	/**
	 * @return the soutProductName
	 */
	public String getSoutProductName() {
		return soutProductName;
	}
	/**
	 * @param soutProductName the soutProductName to set
	 */
	public void setSoutProductName(String soutProductName) {
		this.soutProductName = soutProductName;
	}
	/**
	 * @return the soutNetAmount
	 */
	public String getSoutNetAmount() {
		return soutNetAmount;
	}
	/**
	 * @param soutNetAmount the soutNetAmount to set
	 */
	public void setSoutNetAmount(String soutNetAmount) {
		this.soutNetAmount = soutNetAmount;
	}
	/**
	 * @return the soutTaxType
	 */
	public String getSoutTaxType() {
		return soutTaxType;
	}
	/**
	 * @param soutTaxType the soutTaxType to set
	 */
	public void setSoutTaxType(String soutTaxType) {
		this.soutTaxType = soutTaxType;
	}
	/**
	 * @return the soutCrossTotal
	 */
	public String getSoutCrossTotal() {
		return soutCrossTotal;
	}
	/**
	 * @param soutCrossTotal the soutCrossTotal to set
	 */
	public void setSoutCrossTotal(String soutCrossTotal) {
		this.soutCrossTotal = soutCrossTotal;
	}
	/**
	 * @return the soutTotalAmount
	 */
	public String getSoutTotalAmount() {
		return soutTotalAmount;
	}
	/**
	 * @param soutTotalAmount the soutTotalAmount to set
	 */
	public void setSoutTotalAmount(String soutTotalAmount) {
		this.soutTotalAmount = soutTotalAmount;
	}

	/**
	 * @return the soutProjectnames
	 */
	public String getSoutProjectnames() {
		return soutProjectnames;
	}

	/**
	 * @param soutProjectnames the soutProjectnames to set
	 */
	public void setSoutProjectnames(String soutProjectnames) {
		this.soutProjectnames = soutProjectnames;
	}
	
	
	

}
