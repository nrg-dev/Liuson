package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stockout_recored database table.
 * 
 */
@Entity
@Table(name="stockout_recored")
@NamedQuery(name="StockoutRecored.findAll", query="SELECT s FROM StockoutRecored s")
public class StockoutRecored implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stockout_recored_ID;
	
	@Column(name="damage_quantity")
	private String damageQuantity;

	@Column(name="damage_status")
	private String damageStatus;

	@Column(name="net_amount")
	private String netAmount;
	
	@Column(name="unit_price")
	private String unitPrice;
	
	@Column(name="sold_quantity")
	private String soldQuantity;

	private String status;
	
	//bi-directional many-to-one association to I0021
		@ManyToOne
		@JoinColumn(name="stock_out_ID")
		private StockoutManual stockoutManual;
		
	//bi-directional many-to-one association to Barcode
		@ManyToOne
		@JoinColumn(name="barcode_ID")
		private Barcode barcode;
		
	public Barcode getBarcode() {
			return barcode;
		}

		public void setBarcode(Barcode barcode) {
			this.barcode = barcode;
		}

	public StockoutRecored() {
	}

	public int getStockout_recored_ID() {
		return this.stockout_recored_ID;
	}

	public void setStockout_recored_ID(int stockout_recored_ID) {
		this.stockout_recored_ID = stockout_recored_ID;
	}


	public String getDamageQuantity() {
		return this.damageQuantity;
	}

	public void setDamageQuantity(String damageQuantity) {
		this.damageQuantity = damageQuantity;
	}

	public String getDamageStatus() {
		return this.damageStatus;
	}

	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	public String getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
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

	public StockoutManual getStockoutManual() {
		return stockoutManual;
	}

	public void setStockoutManual(StockoutManual stockoutManual) {
		this.stockoutManual = stockoutManual;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	
}