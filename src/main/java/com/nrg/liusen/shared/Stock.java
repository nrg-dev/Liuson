package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stock_ID;

	@Column(name="login_status")
	private String loginStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="stock_in_date")
	private Date stockInDate;

	@Column(name="stock_in_time")
	private Timestamp stockInTime;

	//bi-directional many-to-one association to Batch
	@OneToMany(mappedBy="stock")
	private List<Batch> batches;

	//bi-directional many-to-one association to Purchase
	@ManyToOne
	@JoinColumn(name="purchase_ID")
	private Purchase purchase;

	public Stock() {
	}

	public int getStock_ID() {
		return this.stock_ID;
	}

	public void setStock_ID(int stock_ID) {
		this.stock_ID = stock_ID;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStockInDate() {
		return this.stockInDate;
	}

	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}

	public Timestamp getStockInTime() {
		return this.stockInTime;
	}

	public void setStockInTime(Timestamp stockInTime) {
		this.stockInTime = stockInTime;
	}

	public List<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	public Batch addBatch(Batch batch) {
		getBatches().add(batch);
		batch.setStock(this);

		return batch;
	}

	public Batch removeBatch(Batch batch) {
		getBatches().remove(batch);
		batch.setStock(null);

		return batch;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

}