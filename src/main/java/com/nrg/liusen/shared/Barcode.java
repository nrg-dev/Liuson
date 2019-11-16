package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the barcode database table.
 * 
 */
@Entity
@NamedQuery(name="Barcode.findAll", query="SELECT b FROM Barcode b")
public class Barcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int barcode_ID;

	private String status;

	private String status2;

	@Column(name="stock_in")
	private String stockIn;

	@Column(name="stock_out")
	private String stockOut;

	private int stock_out_ID;

	//bi-directional many-to-one association to Batch
	@ManyToOne
	@JoinColumn(name="batch_ID")
	private Batch batch;

	//bi-directional many-to-one association to Sale
	@ManyToOne
	@JoinColumn(name="sales_ID")
	private Sale sale;

	//bi-directional many-to-one association to SalesRecord
	@OneToMany(mappedBy="barcode")
	private List<SalesRecord> salesRecords;

	public Barcode() {
	}

	public int getBarcode_ID() {
		return this.barcode_ID;
	}

	public void setBarcode_ID(int barcode_ID) {
		this.barcode_ID = barcode_ID;
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

	public String getStockIn() {
		return this.stockIn;
	}

	public void setStockIn(String stockIn) {
		this.stockIn = stockIn;
	}

	public String getStockOut() {
		return this.stockOut;
	}

	public void setStockOut(String stockOut) {
		this.stockOut = stockOut;
	}

	public int getStock_out_ID() {
		return this.stock_out_ID;
	}

	public void setStock_out_ID(int stock_out_ID) {
		this.stock_out_ID = stock_out_ID;
	}

	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Sale getSale() {
		return this.sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public List<SalesRecord> getSalesRecords() {
		return this.salesRecords;
	}

	public void setSalesRecords(List<SalesRecord> salesRecords) {
		this.salesRecords = salesRecords;
	}

	public SalesRecord addSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().add(salesRecord);
		salesRecord.setBarcode(this);

		return salesRecord;
	}

	public SalesRecord removeSalesRecord(SalesRecord salesRecord) {
		getSalesRecords().remove(salesRecord);
		salesRecord.setBarcode(null);

		return salesRecord;
	}

}