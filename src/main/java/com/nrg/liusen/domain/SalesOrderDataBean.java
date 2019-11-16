package com.nrg.liusen.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.nrg.liusen.shared.Barcode;
import com.nrg.liusen.shared.Batch;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Employee;

public class SalesOrderDataBean
{
	public String message;
	public int salesId=0;	
	public String tick="false";
	public String text="false";
	public String returnqauntity;
	public String dr;
	public String retstatus;
	public String retamount;
	public int salesrecid; 	
	private Date saleDate;
	private Date saleEstDate;
	private String saleCategory;
	private String saleCustomerName;
	private String saleStaffId;
	private String saleProjectName;
	private String saleTaxType;
	private String saleCrossTotal;
	private String saleTotalAmount;
	private String salePrice;
	private String saleNetAmount;
	private String saleProductName;
	private String saleQuantity;
	private String saleSerialNo;
	private Date salFromDate;
	private Date saleToDate;
	private String saleSalesOrderNo;
	private String saleServiceName;
	private String pflag="none";
	private String pflag1;
	private String qflag="none";
	private String qflag1="none";	
	private String appstatus;
	private String reason;
	private String action;
	private String status;
	private String limit;
	private String paidamount;
	private String balanceamount;
	private int payid;
	private String paymenttype;
	private String bankname;
	private String accountno;
	private String chequeno;
	private Date chequedate;
	private String disType;
	private String disamnt;
	ArrayList<SalesOrderDataBean> salesList=new ArrayList<SalesOrderDataBean>();	
	List<Customer> customerNames=null;
	List<Employee> employeeId=null;
	List<String> product=null;
	List<String> projectProduct=null;
	List<String> serviceProduct=null;
	List<Batch> batch=null;
	List<Barcode> barcode=null;
	List<String> projetnames=null;
	List<SalesOrderDataBean> salist=null;
	
	public String getDisType() {
		return disType;
	}
	public void setDisType(String disType) {
		this.disType = disType;
	}
	public String getDisamnt() {
		return disamnt;
	}
	public void setDisamnt(String disamnt) {
		this.disamnt = disamnt;
	}
	public List<String> getProjetnames() {
		return projetnames;
	}
	public void setProjetnames(List<String> projetnames) {
		this.projetnames = projetnames;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public Date getChequedate() {
		return chequedate;
	}
	public void setChequedate(Date chequedate) {
		this.chequedate = chequedate;
	}
	public int getPayid() {
		return payid;
	}
	public void setPayid(int payid) {
		this.payid = payid;
	}
	public String getPaidamount() {
		return paidamount;
	}
	public void setPaidamount(String paidamount) {
		this.paidamount = paidamount;
	}
	public String getBalanceamount() {
		return balanceamount;
	}
	public void setBalanceamount(String balanceamount) {
		this.balanceamount = balanceamount;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAppstatus() {
		return appstatus;
	}
	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}
	public List<SalesOrderDataBean> getSalist() {
		return salist;
	}
	public void setSalist(List<SalesOrderDataBean> salist) {
		this.salist = salist;
	}
	public int getSalesId() {
		return salesId;
	}
	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Batch> getBatch() {
		return batch;
	}
	public void setBatch(List<Batch> batch) {
		this.batch = batch;
	}
	public List<Barcode> getBarcode() {
		return barcode;
	}
	public void setBarcode(List<Barcode> barcode) {
		this.barcode = barcode;
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
	public String getSaleServiceName() {
		return saleServiceName;
	}
	public void setSaleServiceName(String saleServiceName) {
		this.saleServiceName = saleServiceName;
	}
	public List<String> getProjectProduct() {
		return projectProduct;
	}
	public void setProjectProduct(List<String> projectProduct) {
		this.projectProduct = projectProduct;
	}
	public List<String> getServiceProduct() {
		return serviceProduct;
	}
	public void setServiceProduct(List<String> serviceProduct) {
		this.serviceProduct = serviceProduct;
	}
	public List<String> getProduct() {
		return product;
	}
	public void setProduct(List<String> product) {
		this.product = product;
	}
	public List<Employee> getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(List<Employee> employeeId) {
		this.employeeId = employeeId;
	}
	public List<Customer> getCustomerNames() {
		return customerNames;
	}
	public void setCustomerNames(List<Customer> customerNames) {
		this.customerNames = customerNames;
	}
	/**
	 * @return the saleSalesOrderNo
	 */
	public String getSaleSalesOrderNo() {
		return saleSalesOrderNo;
	}
	/**
	 * @param saleSalesOrderNo the saleSalesOrderNo to set
	 */
	public void setSaleSalesOrderNo(String saleSalesOrderNo) {
		this.saleSalesOrderNo = saleSalesOrderNo;
	}
	/**
	 * @return the salFromDate
	 */
	public Date getSalFromDate() {
		return salFromDate;
	}
	/**
	 * @param salFromDate the salFromDate to set
	 */
	public void setSalFromDate(Date salFromDate) {
		this.salFromDate = salFromDate;
	}
	/**
	 * @return the saleToDate
	 */
	public Date getSaleToDate() {
		return saleToDate;
	}
	/**
	 * @param saleToDate the saleToDate to set
	 */
	public void setSaleToDate(Date saleToDate) {
		this.saleToDate = saleToDate;
	}
	/**
	 * @return the saleSerialNo
	 */
	public String getSaleSerialNo() {
		return saleSerialNo;
	}
	/**
	 * @param saleSerialNo the saleSerialNo to set
	 */
	public void setSaleSerialNo(String saleSerialNo) {
		this.saleSerialNo = saleSerialNo;
	}
	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}
	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	/**
	 * @return the saleEstDate
	 */
	public Date getSaleEstDate() {
		return saleEstDate;
	}
	/**
	 * @param saleEstDate the saleEstDate to set
	 */
	public void setSaleEstDate(Date saleEstDate) {
		this.saleEstDate = saleEstDate;
	}
	/**
	 * @return the saleCategory
	 */
	public String getSaleCategory() {
		return saleCategory;
	}
	/**
	 * @param saleCategory the saleCategory to set
	 */
	public void setSaleCategory(String saleCategory) {
		this.saleCategory = saleCategory;
	}
	/**
	 * @return the saleCustomerName
	 */
	public String getSaleCustomerName() {
		return saleCustomerName;
	}
	/**
	 * @param saleCustomerName the saleCustomerName to set
	 */
	public void setSaleCustomerName(String saleCustomerName) {
		this.saleCustomerName = saleCustomerName;
	}
	/**
	 * @return the saleStaffId
	 */
	public String getSaleStaffId() {
		return saleStaffId;
	}
	/**
	 * @param saleStaffId the saleStaffId to set
	 */
	public void setSaleStaffId(String saleStaffId) {
		this.saleStaffId = saleStaffId;
	}
	/**
	 * @return the saleProjectName
	 */
	public String getSaleProjectName() {
		return saleProjectName;
	}
	/**
	 * @param saleProjectName the saleProjectName to set
	 */
	public void setSaleProjectName(String saleProjectName) {
		this.saleProjectName = saleProjectName;
	}
	/**
	 * @return the saleTaxType
	 */
	public String getSaleTaxType() {
		return saleTaxType;
	}
	/**
	 * @param saleTaxType the saleTaxType to set
	 */
	public void setSaleTaxType(String saleTaxType) {
		this.saleTaxType = saleTaxType;
	}
	/**
	 * @return the saleCrossTotal
	 */
	public String getSaleCrossTotal() {
		return saleCrossTotal;
	}
	/**
	 * @param saleCrossTotal the saleCrossTotal to set
	 */
	public void setSaleCrossTotal(String saleCrossTotal) {
		this.saleCrossTotal = saleCrossTotal;
	}
	/**
	 * @return the saleTotalAmount
	 */
	public String getSaleTotalAmount() {
		return saleTotalAmount;
	}
	/**
	 * @param saleTotalAmount the saleTotalAmount to set
	 */
	public void setSaleTotalAmount(String saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}
	/**
	 * @return the salesList
	 */
	public ArrayList<SalesOrderDataBean> getSalesList() {
		return salesList;
	}
	/**
	 * @param salesList the salesList to set
	 */
	public void setSalesList(ArrayList<SalesOrderDataBean> salesList) {
		this.salesList = salesList;
	}
	/**
	 * @return the salePrice
	 */
	public String getSalePrice() {
		return salePrice;
	}
	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	/**
	 * @return the saleNetAmount
	 */
	public String getSaleNetAmount() {
		return saleNetAmount;
	}
	/**
	 * @param saleNetAmount the saleNetAmount to set
	 */
	public void setSaleNetAmount(String saleNetAmount) {
		this.saleNetAmount = saleNetAmount;
	}
	/**
	 * @return the saleProductName
	 */
	public String getSaleProductName() {
		return saleProductName;
	}
	/**
	 * @param saleProductName the saleProductName to set
	 */
	public void setSaleProductName(String saleProductName) {
		this.saleProductName = saleProductName;
	}
	/**
	 * @return the saleQuantity
	 */
	public String getSaleQuantity() {
		return saleQuantity;
	}
	/**
	 * @param saleQuantity the saleQuantity to set
	 */
	public void setSaleQuantity(String saleQuantity) {
		this.saleQuantity = saleQuantity;
	}	
	public int getSalesrecid() {
		return salesrecid;
	}
	public void setSalesrecid(int salesrecid) {
		this.salesrecid = salesrecid;
	}
	public String getRetamount() {
		return retamount;
	}
	public void setRetamount(String retamount) {
		this.retamount = retamount;
	}
	public String getRetstatus() {
		return retstatus;
	}
	public void setRetstatus(String retstatus) {
		this.retstatus = retstatus;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public String getReturnqauntity() {
		return returnqauntity;
	}
	public void setReturnqauntity(String returnqauntity) {
		this.returnqauntity = returnqauntity;
	}
	public String getTick() {
		return tick;
	}
	public void setTick(String tick) {
		this.tick = tick;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
