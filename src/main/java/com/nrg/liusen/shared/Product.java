package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int product_ID;

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

	//bi-directional many-to-one association to Batch
	@OneToMany(mappedBy="product")
	private List<Batch> batches;

	//bi-directional many-to-one association to Limit
	@OneToMany(mappedBy="product")
	private List<Limit> limits;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_ID")
	private Vendor vendor;

	//bi-directional many-to-one association to ProductLimit
	@OneToMany(mappedBy="product")
	private List<ProductLimit> productLimits;

	//bi-directional many-to-one association to PurchaseRecord
	@OneToMany(mappedBy="product")
	private List<PurchaseRecord> purchaseRecords;

	//bi-directional many-to-one association to SalesRecord
	@OneToMany(mappedBy="product")
	private List<SalesRecord> salesRecords;

	//bi-directional many-to-one association to VendorProduct
	@OneToMany(mappedBy="product")
	private List<VendorProduct> vendorProducts;

	public Product() {
	}

	public int getProduct_ID() {
		return this.product_ID;
	}

	public void setProduct_ID(int product_ID) {
		this.product_ID = product_ID;
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

	public List<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	public Batch addBatch(Batch batch) {
		getBatches().add(batch);
		batch.setProduct(this);

		return batch;
	}

	public Batch removeBatch(Batch batch) {
		getBatches().remove(batch);
		batch.setProduct(null);

		return batch;
	}

	public List<Limit> getLimits() {
		return this.limits;
	}

	public void setLimits(List<Limit> limits) {
		this.limits = limits;
	}

	public Limit addLimit(Limit limit) {
		getLimits().add(limit);
		limit.setProduct(this);

		return limit;
	}

	public Limit removeLimit(Limit limit) {
		getLimits().remove(limit);
		limit.setProduct(null);

		return limit;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<ProductLimit> getProductLimits() {
		return this.productLimits;
	}

	public void setProductLimits(List<ProductLimit> productLimits) {
		this.productLimits = productLimits;
	}

	public ProductLimit addProductLimit(ProductLimit productLimit) {
		getProductLimits().add(productLimit);
		productLimit.setProduct(this);

		return productLimit;
	}

	public ProductLimit removeProductLimit(ProductLimit productLimit) {
		getProductLimits().remove(productLimit);
		productLimit.setProduct(null);

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
		purchaseRecord.setProduct(this);

		return purchaseRecord;
	}

	public PurchaseRecord removePurchaseRecord(PurchaseRecord purchaseRecord) {
		getPurchaseRecords().remove(purchaseRecord);
		purchaseRecord.setProduct(null);

		return purchaseRecord;
	}

	public List<SalesRecord> getSalesRecords() {
		return this.salesRecords;
	}

	public void setSalesRecords(List<SalesRecord> salesRecords) {
		this.salesRecords = salesRecords;
	}

	public SalesRecord addSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().add(salesRecord);
		salesRecord.setProduct(this);

		return salesRecord;
	}

	public SalesRecord removeSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().remove(salesRecord);
		salesRecord.setProduct(null);

		return salesRecord;
	}

	public List<VendorProduct> getVendorProducts() {
		return this.vendorProducts;
	}

	public void setVendorProducts(List<VendorProduct> vendorProducts) {
		this.vendorProducts = vendorProducts;
	}

	public VendorProduct addVendorProduct(VendorProduct vendorProduct) {
		getVendorProducts().add(vendorProduct);
		vendorProduct.setProduct(this);

		return vendorProduct;
	}

	public VendorProduct removeVendorProduct(VendorProduct vendorProduct) {
		getVendorProducts().remove(vendorProduct);
		vendorProduct.setProduct(null);

		return vendorProduct;
	}

	//bi-directional many-to-one association to ImagePath
		@OneToMany(mappedBy="product")
		private List<ImagePath> imagePaths;
		
		public List<ImagePath> getImagePaths() {
			return this.imagePaths;
		}

		public void setImagePaths(List<ImagePath> imagePaths) {
			this.imagePaths = imagePaths;
		}
}