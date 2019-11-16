package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;

import com.nrg.liusen.shared.Customer;
/**
 * 
 * @author Robert Arjun
 * @date 26-10-2015
 * @copyright NRG This class used to hold the data( POJO Class )
 */

public class CustomerDataBean {

	private String custCustomerName;
	private Date custDate;
	private String custShipingAddress;
	private String custCity;
	private String custState;
	private String custCountry;
	private String custPhoneNumber;
	private String custEmail;
	private String custTaxNO;
	private String custNote;
	private String custStaffName;
	private String custStaffId;
	List<Customer> cusphn=null;
	List<Customer> cuslist=null;
	
	public List<Customer> getCuslist() {
		return cuslist;
	}
	public void setCuslist(List<Customer> cuslist) {
		this.cuslist = cuslist;
	}
	/**
	 * @return the custStaffName
	 */
public List<Customer> getCusphn() {
	return cusphn;
}
public void setCusphn(List<Customer> cusphn) {
	this.cusphn = cusphn;
}
	public String getCustStaffName() {
		return custStaffName;
	}
	public String getCustStaffId() {
		return custStaffId;
	}
	public void setCustStaffId(String custStaffId) {
		this.custStaffId = custStaffId;
	}
	/**
	 * @param custStaffName the custStaffName to set
	 */
	public void setCustStaffName(String custStaffName) {
		this.custStaffName = custStaffName;
	}
	/**
	 * @return the custCustomerName
	 */
	public String getCustCustomerName() {
		return custCustomerName;
	}
	/**
	 * @param custCustomerName the custCustomerName to set
	 */
	public void setCustCustomerName(String custCustomerName) {
		this.custCustomerName = custCustomerName;
	}
	
	/**
	 * @return the custDate
	 */
	public Date getCustDate() {
		return custDate;
	}
	/**
	 * @param custDate the custDate to set
	 */
	public void setCustDate(Date custDate) {
		this.custDate = custDate;
	}
	/**
	 * @return the custShipingAddress
	 */
	public String getCustShipingAddress() {
		return custShipingAddress;
	}
	/**
	 * @param custShipingAddress the custShipingAddress to set
	 */
	public void setCustShipingAddress(String custShipingAddress) {
		this.custShipingAddress = custShipingAddress;
	}
	/**
	 * @return the custCity
	 */
	public String getCustCity() {
		return custCity;
	}
	/**
	 * @param custCity the custCity to set
	 */
	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}
	/**
	 * @return the custState
	 */
	public String getCustState() {
		return custState;
	}
	/**
	 * @param custState the custState to set
	 */
	public void setCustState(String custState) {
		this.custState = custState;
	}
	/**
	 * @return the custCountry
	 */
	public String getCustCountry() {
		return custCountry;
	}
	/**
	 * @param custCountry the custCountry to set
	 */
	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}
	/**
	 * @return the custPhoneNumber
	 */
	public String getCustPhoneNumber() {
		return custPhoneNumber;
	}
	/**
	 * @param custPhoneNumber the custPhoneNumber to set
	 */
	public void setCustPhoneNumber(String custPhoneNumber) {
		this.custPhoneNumber = custPhoneNumber;
	}
	/**
	 * @return the custEmail
	 */
	public String getCustEmail() {
		return custEmail;
	}
	/**
	 * @param custEmail the custEmail to set
	 */
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	/**
	 * @return the custTaxNO
	 */
	public String getCustTaxNO() {
		return custTaxNO;
	}
	/**
	 * @param custTaxNO the custTaxNO to set
	 */
	public void setCustTaxNO(String custTaxNO) {
		this.custTaxNO = custTaxNO;
	}
	/**
	 * @return the custNote
	 */
	public String getCustNote() {
		return custNote;
	}
	/**
	 * @param custNote the custNote to set
	 */
	public void setCustNote(String custNote) {
		this.custNote = custNote;
	}
	
	
}
