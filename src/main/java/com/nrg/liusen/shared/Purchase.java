package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the purchase database table.
 * 
 */
@Entity
@NamedQuery(name="Purchase.findAll", query="SELECT p FROM Purchase p")
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_ID;

	private String category;

	@Column(name="cross_total")
	private String crossTotal;

	@Column(name="delivery_status")
	private String deliveryStatus;

	@Column(name="discount_amount")
	private String discountAmount;

	@Column(name="discount_type")
	private String discountType;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="payment_status")
	private String paymentStatus;

	@Column(name="purchase_approval_status")
	private String purchaseApprovalStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_date")
	private Date purchaseDate;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_estimated_date")
	private Date purchaseEstimatedDate;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_order_date")
	private Date purchaseOrderDate;

	@Column(name="purchase_order_number")
	private String purchaseOrderNumber;

	@Column(name="purchase_time")
	private Timestamp purchaseTime;

	private String status;

	private String status2;

	@Column(name="total_amount")
	private String totalAmount;

	@Column(name="typeof_project")
	private String typeofProject;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_ID")
	private Vendor vendor;

	//bi-directional many-to-one association to PurchaseApproval
	@OneToMany(mappedBy="purchase")
	private List<PurchaseApproval> purchaseApprovals;

	//bi-directional many-to-one association to PurchaseRecord
	@OneToMany(mappedBy="purchase")
	private List<PurchaseRecord> purchaseRecords;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="purchase")
	private List<Stock> stocks;

	public Purchase() {
	}

	public int getPurchase_ID() {
		return this.purchase_ID;
	}

	public void setPurchase_ID(int purchase_ID) {
		this.purchase_ID = purchase_ID;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCrossTotal() {
		return this.crossTotal;
	}

	public void setCrossTotal(String crossTotal) {
		this.crossTotal = crossTotal;
	}

	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPurchaseApprovalStatus() {
		return this.purchaseApprovalStatus;
	}

	public void setPurchaseApprovalStatus(String purchaseApprovalStatus) {
		this.purchaseApprovalStatus = purchaseApprovalStatus;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getPurchaseEstimatedDate() {
		return this.purchaseEstimatedDate;
	}

	public void setPurchaseEstimatedDate(Date purchaseEstimatedDate) {
		this.purchaseEstimatedDate = purchaseEstimatedDate;
	}

	public Date getPurchaseOrderDate() {
		return this.purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public String getPurchaseOrderNumber() {
		return this.purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public Timestamp getPurchaseTime() {
		return this.purchaseTime;
	}

	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus2() {
		return this.status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTypeofProject() {
		return this.typeofProject;
	}

	public void setTypeofProject(String typeofProject) {
		this.typeofProject = typeofProject;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<PurchaseApproval> getPurchaseApprovals() {
		return this.purchaseApprovals;
	}

	public void setPurchaseApprovals(List<PurchaseApproval> purchaseApprovals) {
		this.purchaseApprovals = purchaseApprovals;
	}

	public PurchaseApproval addPurchaseApproval(PurchaseApproval purchaseApproval) {
		getPurchaseApprovals().add(purchaseApproval);
		purchaseApproval.setPurchase(this);

		return purchaseApproval;
	}

	public PurchaseApproval removePurchaseApproval(PurchaseApproval purchaseApproval) {
		getPurchaseApprovals().remove(purchaseApproval);
		purchaseApproval.setPurchase(null);

		return purchaseApproval;
	}

	public List<PurchaseRecord> getPurchaseRecords() {
		return this.purchaseRecords;
	}

	public void setPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
		this.purchaseRecords = purchaseRecords;
	}

	public PurchaseRecord addPurchaseRecord(PurchaseRecord purchaseRecord) {
		getPurchaseRecords().add(purchaseRecord);
		purchaseRecord.setPurchase(this);

		return purchaseRecord;
	}

	public PurchaseRecord removePurchaseRecord(PurchaseRecord purchaseRecord) {
		getPurchaseRecords().remove(purchaseRecord);
		purchaseRecord.setPurchase(null);

		return purchaseRecord;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setPurchase(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setPurchase(null);

		return stock;
	}

}