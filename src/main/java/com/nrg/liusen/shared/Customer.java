package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int customer_ID;

	@Column(name="customer_address")
	private String customerAddress;

	@Column(name="customer_city")
	private String customerCity;

	@Column(name="customer_description")
	private String customerDescription;

	@Column(name="customer_email")
	private String customerEmail;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="customer_phone_number")
	private String customerPhoneNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="customer_reg_date")
	private Date customerRegDate;

	@Column(name="customer_reg_time")
	private Timestamp customerRegTime;

	@Column(name="customer_state")
	private String customerState;

	@Column(name="customer_tax_number")
	private String customerTaxNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="login_status")
	private String loginStatus;

	private String status;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country_ID")
	private Country country;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;

	//bi-directional many-to-one association to Sale
	@OneToMany(mappedBy="customer")
	private List<Sale> sales;

	//bi-directional many-to-one association to StockoutManual
	@OneToMany(mappedBy="customer")
	private List<StockoutManual> stockoutManuals;

	public Customer() {
	}

	public int getCustomer_ID() {
		return this.customer_ID;
	}

	public void setCustomer_ID(int customer_ID) {
		this.customer_ID = customer_ID;
	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerDescription() {
		return this.customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return this.customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public Date getCustomerRegDate() {
		return this.customerRegDate;
	}

	public void setCustomerRegDate(Date customerRegDate) {
		this.customerRegDate = customerRegDate;
	}

	public Timestamp getCustomerRegTime() {
		return this.customerRegTime;
	}

	public void setCustomerRegTime(Timestamp customerRegTime) {
		this.customerRegTime = customerRegTime;
	}

	public String getCustomerState() {
		return this.customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerTaxNumber() {
		return this.customerTaxNumber;
	}

	public void setCustomerTaxNumber(String customerTaxNumber) {
		this.customerTaxNumber = customerTaxNumber;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getEditLoginStatus() {
		return this.editLoginStatus;
	}

	public void setEditLoginStatus(String editLoginStatus) {
		this.editLoginStatus = editLoginStatus;
	}

	public String getEditStatus() {
		return this.editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Sale> getSales() {
		return this.sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Sale addSale(Sale sale) {
		getSales().add(sale);
		sale.setCustomer(this);

		return sale;
	}

	public Sale removeSale(Sale sale) {
		getSales().remove(sale);
		sale.setCustomer(null);

		return sale;
	}

	public List<StockoutManual> getStockoutManuals() {
		return this.stockoutManuals;
	}

	public void setStockoutManuals(List<StockoutManual> stockoutManuals) {
		this.stockoutManuals = stockoutManuals;
	}

	public StockoutManual addStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().add(stockoutManual);
		stockoutManual.setCustomer(this);

		return stockoutManual;
	}

	public StockoutManual removeStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().remove(stockoutManual);
		stockoutManual.setCustomer(null);

		return stockoutManual;
	}

}