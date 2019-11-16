package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the raw_material database table.
 * 
 */
@Entity
@Table(name="raw_material")
@NamedQuery(name="RawMaterial.findAll", query="SELECT r FROM RawMaterial r")
public class RawMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int raw_ID;

	@Column(name="actual_price")
	private String actualPrice;

	private String brand;

	private String category;

	@Column(name="category_status")
	private String categoryStatus;

	private String color;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="market_price")
	private String marketPrice;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	@Temporal(TemporalType.DATE)
	@Column(name="product_reg_date")
	private Date productRegDate;

	@Column(name="product_reg_time")
	private Timestamp productRegTime;

	private String size;

	private String status;

	private String subcategory1_ID;

	private String subcategory2_ID;

	private String unit;

	//bi-directional many-to-one association to ProductLimit
	@OneToMany(mappedBy="rawMaterial")
	private List<ProductLimit> productLimits;

	//bi-directional many-to-one association to PurchaseRecord
	@OneToMany(mappedBy="rawMaterial")
	private List<PurchaseRecord> purchaseRecords;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_ID")
	private Vendor vendor;

	//bi-directional many-to-one association to SalesRecord
	@OneToMany(mappedBy="rawMaterial")
	private List<SalesRecord> salesRecords;

	public RawMaterial() {
	}

	public int getRaw_ID() {
		return this.raw_ID;
	}

	public void setRaw_ID(int raw_ID) {
		this.raw_ID = raw_ID;
	}

	public String getActualPrice() {
		return this.actualPrice;
	}

	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryStatus() {
		return this.categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getProductRegDate() {
		return this.productRegDate;
	}

	public void setProductRegDate(Date productRegDate) {
		this.productRegDate = productRegDate;
	}

	public Timestamp getProductRegTime() {
		return this.productRegTime;
	}

	public void setProductRegTime(Timestamp productRegTime) {
		this.productRegTime = productRegTime;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubcategory1_ID() {
		return this.subcategory1_ID;
	}

	public void setSubcategory1_ID(String subcategory1_ID) {
		this.subcategory1_ID = subcategory1_ID;
	}

	public String getSubcategory2_ID() {
		return this.subcategory2_ID;
	}

	public void setSubcategory2_ID(String subcategory2_ID) {
		this.subcategory2_ID = subcategory2_ID;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<ProductLimit> getProductLimits() {
		return this.productLimits;
	}

	public void setProductLimits(List<ProductLimit> productLimits) {
		this.productLimits = productLimits;
	}

	public ProductLimit addProductLimit(ProductLimit productLimit) {
		getProductLimits().add(productLimit);
		productLimit.setRawMaterial(this);

		return productLimit;
	}

	public ProductLimit removeProductLimit(ProductLimit productLimit) {
		getProductLimits().remove(productLimit);
		productLimit.setRawMaterial(null);

		return productLimit;
	}

	public List<PurchaseRecord> getPurchaseRecords() {
		return this.purchaseRecords;
	}

	public void setPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
		this.purchaseRecords = purchaseRecords;
	}

	public PurchaseRecord addPurchaseRecord(PurchaseRecord purchaseRecord) {
		getPurchaseRecords().add(purchaseRecord);
		purchaseRecord.setRawMaterial(this);

		return purchaseRecord;
	}

	public PurchaseRecord removePurchaseRecord(PurchaseRecord purchaseRecord) {
		getPurchaseRecords().remove(purchaseRecord);
		purchaseRecord.setRawMaterial(null);

		return purchaseRecord;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<SalesRecord> getSalesRecords() {
		return this.salesRecords;
	}

	public void setSalesRecords(List<SalesRecord> salesRecords) {
		this.salesRecords = salesRecords;
	}

	public SalesRecord addSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().add(salesRecord);
		salesRecord.setRawMaterial(this);

		return salesRecord;
	}

	public SalesRecord removeSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().remove(salesRecord);
		salesRecord.setRawMaterial(null);

		return salesRecord;
	}

}