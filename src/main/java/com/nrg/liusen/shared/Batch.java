package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the batch database table.
 * 
 */
@Entity
@NamedQuery(name="Batch.findAll", query="SELECT b FROM Batch b")
public class Batch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int batch_ID;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="product_name")
	private String productName;

	private String status;
	
	private String category;
	
	

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	private String unit;

	@Column(name="unit_price")
	private String unitPrice;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_ID")
	private Vendor vendor;

	//bi-directional many-to-one association to Barcode
	@OneToMany(mappedBy="batch")
	private List<Barcode> barcodes;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to Stock
	@ManyToOne
	@JoinColumn(name="stock_ID")
	private Stock stock;

	//bi-directional many-to-one association to StockDamage
	@OneToMany(mappedBy="batch")
	private List<StockDamage> stockDamages;

	public Batch() {
	}

	public int getBatch_ID() {
		return this.batch_ID;
	}

	public void setBatch_ID(int batch_ID) {
		this.batch_ID = batch_ID;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}


	/**
	 * @return the vendor
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<Barcode> getBarcodes() {
		return this.barcodes;
	}

	public void setBarcodes(List<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	public Barcode addBarcode(Barcode barcode) {
		getBarcodes().add(barcode);
		barcode.setBatch(this);

		return barcode;
	}

	public Barcode removeBarcode(Barcode barcode) {
		getBarcodes().remove(barcode);
		barcode.setBatch(null);

		return barcode;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public List<StockDamage> getStockDamages() {
		return this.stockDamages;
	}

	public void setStockDamages(List<StockDamage> stockDamages) {
		this.stockDamages = stockDamages;
	}

	public StockDamage addStockDamage(StockDamage stockDamage) {
		getStockDamages().add(stockDamage);
		stockDamage.setBatch(this);

		return stockDamage;
	}

	public StockDamage removeStockDamage(StockDamage stockDamage) {
		getStockDamages().remove(stockDamage);
		stockDamage.setBatch(null);

		return stockDamage;
	}

}