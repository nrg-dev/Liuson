package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vendor_product database table.
 * 
 */
@Entity
@Table(name="vendor_product")
@NamedQuery(name="VendorProduct.findAll", query="SELECT v FROM VendorProduct v")
public class VendorProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_vendor_ID;

	private String status;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_ID")
	private Vendor vendor;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	public VendorProduct() {
	}

	public int getHas_vendor_ID() {
		return this.has_vendor_ID;
	}

	public void setHas_vendor_ID(int has_vendor_ID) {
		this.has_vendor_ID = has_vendor_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}