package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sales_record database table.
 * 
 */
@Entity
@Table(name="sales_record")
@NamedQuery(name="SalesRecord.findAll", query="SELECT s FROM SalesRecord s")
public class SalesRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_record_ID;

	@Column(name="damage_return_quantity")
	private String damageReturnQuantity;

	@Column(name="damage_status")
	private String damageStatus;

	private int sales_ID;

	@Column(name="sold_quantity")
	private String soldQuantity;

	private String status;

	//bi-directional many-to-one association to Barcode
	@ManyToOne
	@JoinColumn(name="barcode_ID")
	private Barcode barcode;

	//bi-directional many-to-one association to RawMaterial
	@ManyToOne
	@JoinColumn(name="raw_ID")
	private RawMaterial rawMaterial;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to SalesReturn
	@OneToMany(mappedBy="salesRecord")
	private List<SalesReturn> salesReturns;

	public SalesRecord() {
	}

	public int getSales_record_ID() {
		return this.sales_record_ID;
	}

	public void setSales_record_ID(int sales_record_ID) {
		this.sales_record_ID = sales_record_ID;
	}

	public String getDamageReturnQuantity() {
		return this.damageReturnQuantity;
	}

	public void setDamageReturnQuantity(String damageReturnQuantity) {
		this.damageReturnQuantity = damageReturnQuantity;
	}

	public String getDamageStatus() {
		return this.damageStatus;
	}

	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	public int getSales_ID() {
		return this.sales_ID;
	}

	public void setSales_ID(int sales_ID) {
		this.sales_ID = sales_ID;
	}

	public String getSoldQuantity() {
		return this.soldQuantity;
	}

	public void setSoldQuantity(String soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Barcode getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	public RawMaterial getRawMaterial() {
		return this.rawMaterial;
	}

	public void setRawMaterial(RawMaterial rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<SalesReturn> getSalesReturns() {
		return this.salesReturns;
	}

	public void setSalesReturns(List<SalesReturn> salesReturns) {
		this.salesReturns = salesReturns;
	}

	public SalesReturn addSalesReturn(SalesReturn salesReturn) {
		getSalesReturns().add(salesReturn);
		salesReturn.setSalesRecord(this);

		return salesReturn;
	}

	public SalesReturn removeSalesReturn(SalesReturn salesReturn) {
		getSalesReturns().remove(salesReturn);
		salesReturn.setSalesRecord(null);

		return salesReturn;
	}

}