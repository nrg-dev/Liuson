package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the sales database table.
 * 
 */
@Entity
@Table(name="sales")
@NamedQuery(name="Sale.findAll", query="SELECT s FROM Sale s")
public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_ID;

	@Column(name="cross_total")
	private String crossTotal;

	@Column(name="discount_amount")
	private String discountAmount;

	@Column(name="discount_type")
	private String discountType;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="payment_status")
	private String paymentStatus;

	@Column(name="project_name")
	private String projectName;

	@Column(name="sales_approval_status")
	private String salesApprovalStatus;

	@Column(name="sales_category")
	private String salesCategory;

	@Temporal(TemporalType.DATE)
	@Column(name="sales_date")
	private Date salesDate;

	@Temporal(TemporalType.DATE)
	@Column(name="sales_delivery_date")
	private Date salesDeliveryDate;

	@Column(name="sales_delivery_status")
	private String salesDeliveryStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="sales_order_date")
	private Date salesOrderDate;

	@Column(name="sales_order_number")
	private String salesOrderNumber;

	@Column(name="sales_time")
	private Timestamp salesTime;

	@Column(name="tax_type")
	private String taxType;

	@Column(name="total_amount")
	private String totalAmount;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="customer_ID")
	private Customer customer;
	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;
	
	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="project_ID")
	private Projet projet;

	public Sale() {
	}

	public int getSales_ID() {
		return this.sales_ID;
	}

	public void setSales_ID(int sales_ID) {
		this.sales_ID = sales_ID;
	}

	public String getCrossTotal() {
		return this.crossTotal;
	}

	public void setCrossTotal(String crossTotal) {
		this.crossTotal = crossTotal;
	}

	public String getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDiscountType() {
		return this.discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSalesApprovalStatus() {
		return this.salesApprovalStatus;
	}

	public void setSalesApprovalStatus(String salesApprovalStatus) {
		this.salesApprovalStatus = salesApprovalStatus;
	}

	public String getSalesCategory() {
		return this.salesCategory;
	}

	public void setSalesCategory(String salesCategory) {
		this.salesCategory = salesCategory;
	}

	public Date getSalesDate() {
		return this.salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public Date getSalesDeliveryDate() {
		return this.salesDeliveryDate;
	}

	public void setSalesDeliveryDate(Date salesDeliveryDate) {
		this.salesDeliveryDate = salesDeliveryDate;
	}

	public String getSalesDeliveryStatus() {
		return this.salesDeliveryStatus;
	}

	public void setSalesDeliveryStatus(String salesDeliveryStatus) {
		this.salesDeliveryStatus = salesDeliveryStatus;
	}

	public Date getSalesOrderDate() {
		return this.salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public String getSalesOrderNumber() {
		return this.salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public Timestamp getSalesTime() {
		return this.salesTime;
	}

	public void setSalesTime(Timestamp salesTime) {
		this.salesTime = salesTime;
	}

	public String getTaxType() {
		return this.taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

}