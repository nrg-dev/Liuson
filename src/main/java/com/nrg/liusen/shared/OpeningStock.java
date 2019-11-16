package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the opening_stock database table.
 * 
 */
@Entity
@Table(name="opening_stock")
@NamedQuery(name="OpeningStock.findAll", query="SELECT o FROM OpeningStock o")
public class OpeningStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="open_id")
	private int openId;

	private String category;

	private String productCode;

	private String productName;

	private String quantity;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="stock_date")
	private Date stockDate;

	public OpeningStock() {
	}

	public int getOpenId() {
		return this.openId;
	}

	public void setOpenId(int openId) {
		this.openId = openId;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public Date getStockDate() {
		return this.stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

}