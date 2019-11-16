package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the stockout_manual database table.
 * 
 */
@Entity
@Table(name="stockout_manual")
@NamedQuery(name="StockoutManual.findAll", query="SELECT s FROM StockoutManual s")
public class StockoutManual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stock_out_ID;

	@Column(name="cross_total")
	private String crossTotal;

	private int has_project_ID;

	@Column(name="limit_approval_status")
	private String limitApprovalStatus;

	@Column(name="limit_status")
	private String limitStatus;

	@Column(name="login_status")
	private String loginStatus;

	private int sales_ID;

	private String status;

	private String category;
	
	@Temporal(TemporalType.DATE)
	@Column(name="stockout_date")
	private Date stockoutDate;

	@Temporal(TemporalType.DATE)
	@Column(name="stockout_delivery_date")
	private Date stockoutDeliveryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="stockout_order_date")
	private Date stockoutOrderDate;

	@Column(name="stockout_order_number")
	private String stockoutOrderNumber;

	@Column(name="stockout_time")
	private Timestamp stockoutTime;

	@Column(name="tax_type")
	private String taxType;

	@Column(name="total_amount")
	private String totalAmount;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_ID")
	private Customer customer;
	
	//bi-directional many-to-one association to Project
		@ManyToOne
		@JoinColumn(name="project_ID")
		private Projet projet;
		
		

	/**
		 * @return the projet
		 */
		public Projet getProjet() {
			return projet;
		}

		/**
		 * @param projet the projet to set
		 */
		public void setProjet(Projet projet) {
			this.projet = projet;
		}

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to SalesTemp
		@OneToMany(mappedBy="stockoutManual")
		private List<StockoutRecored> stockoutRecoreds;
	
	public StockoutManual() {
	}

	public int getStock_out_ID() {
		return this.stock_out_ID;
	}

	public void setStock_out_ID(int stock_out_ID) {
		this.stock_out_ID = stock_out_ID;
	}

	public String getCrossTotal() {
		return this.crossTotal;
	}

	public void setCrossTotal(String crossTotal) {
		this.crossTotal = crossTotal;
	}

	public int getHas_project_ID() {
		return this.has_project_ID;
	}

	public void setHas_project_ID(int has_project_ID) {
		this.has_project_ID = has_project_ID;
	}

	public String getLimitApprovalStatus() {
		return this.limitApprovalStatus;
	}

	public void setLimitApprovalStatus(String limitApprovalStatus) {
		this.limitApprovalStatus = limitApprovalStatus;
	}

	public String getLimitStatus() {
		return this.limitStatus;
	}

	public void setLimitStatus(String limitStatus) {
		this.limitStatus = limitStatus;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public int getSales_ID() {
		return this.sales_ID;
	}

	public void setSales_ID(int sales_ID) {
		this.sales_ID = sales_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStockoutDate() {
		return this.stockoutDate;
	}

	public void setStockoutDate(Date stockoutDate) {
		this.stockoutDate = stockoutDate;
	}

	public Date getStockoutDeliveryDate() {
		return this.stockoutDeliveryDate;
	}

	public void setStockoutDeliveryDate(Date stockoutDeliveryDate) {
		this.stockoutDeliveryDate = stockoutDeliveryDate;
	}

	public Date getStockoutOrderDate() {
		return this.stockoutOrderDate;
	}

	public void setStockoutOrderDate(Date stockoutOrderDate) {
		this.stockoutOrderDate = stockoutOrderDate;
	}

	public String getStockoutOrderNumber() {
		return this.stockoutOrderNumber;
	}

	public void setStockoutOrderNumber(String stockoutOrderNumber) {
		this.stockoutOrderNumber = stockoutOrderNumber;
	}

	public Timestamp getStockoutTime() {
		return this.stockoutTime;
	}

	public void setStockoutTime(Timestamp stockoutTime) {
		this.stockoutTime = stockoutTime;
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
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<StockoutRecored> getStockoutRecoreds() {
		return this.stockoutRecoreds;
	}

	public void setStockoutRecoreds(List<StockoutRecored> stockoutRecoreds) {
		this.stockoutRecoreds = stockoutRecoreds;
	}

	public StockoutRecored addStockoutRecored(StockoutRecored stockoutRecored) {
		getStockoutRecoreds().add(stockoutRecored);
		stockoutRecored.setStockoutManual(this);

		return stockoutRecored;
	}

	public StockoutRecored removeStockoutRecored(StockoutRecored stockoutRecored) {
		getStockoutRecoreds().remove(stockoutRecored);
		stockoutRecored.setStockoutManual(null);

		return stockoutRecored;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}