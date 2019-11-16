package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the purchase_record database table.
 * 
 */
@Entity
@Table(name="purchase_record")
@NamedQuery(name="PurchaseRecord.findAll", query="SELECT p FROM PurchaseRecord p")
public class PurchaseRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_record_ID;

	private String quantity;

	private String status;

	private String status2;

	@Column(name="unit_price")
	private String unitPrice;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to RawMaterial
	@ManyToOne
	@JoinColumn(name="raw_ID")
	private RawMaterial rawMaterial;

	//bi-directional many-to-one association to Purchase
	@ManyToOne
	@JoinColumn(name="purchase_ID")
	private Purchase purchase;

	public PurchaseRecord() {
	}

	public int getPurchase_record_ID() {
		return this.purchase_record_ID;
	}

	public void setPurchase_record_ID(int purchase_record_ID) {
		this.purchase_record_ID = purchase_record_ID;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public String getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public RawMaterial getRawMaterial() {
		return this.rawMaterial;
	}

	public void setRawMaterial(RawMaterial rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

}