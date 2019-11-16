package com.nrg.liusen.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the purchase_return database table.
 * 
 */
@Entity
@Table(name="purchase_return")
@NamedQuery(name="PurchaseReturn.findAll", query="SELECT p FROM PurchaseReturn p")
public class PurchaseReturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_return_ID;

	@Column(name="damage_return")
	private String damageReturn;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="normal_return")
	private String normalReturn;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_return_date")
	private Date purchaseReturnDate;

	@Column(name="purchase_return_time")
	private Timestamp purchaseReturnTime;

	private String status;

	//bi-directional many-to-one association to Product
		@ManyToOne
		@JoinColumn(name="purchase_record_ID")
		private PurchaseRecord purchaseRecord;
	public PurchaseReturn() {
	}

	public int getPurchase_return_ID() {
		return this.purchase_return_ID;
	}

	public void setPurchase_return_ID(int purchase_return_ID) {
		this.purchase_return_ID = purchase_return_ID;
	}

	public String getDamageReturn() {
		return this.damageReturn;
	}

	public void setDamageReturn(String damageReturn) {
		this.damageReturn = damageReturn;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getNormalReturn() {
		return this.normalReturn;
	}

	public void setNormalReturn(String normalReturn) {
		this.normalReturn = normalReturn;
	}

	public Date getPurchaseReturnDate() {
		return this.purchaseReturnDate;
	}

	public void setPurchaseReturnDate(Date purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	public Timestamp getPurchaseReturnTime() {
		return this.purchaseReturnTime;
	}

	public void setPurchaseReturnTime(Timestamp purchaseReturnTime) {
		this.purchaseReturnTime = purchaseReturnTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PurchaseRecord getPurchaseRecord() {
		return purchaseRecord;
	}

	public void setPurchaseRecord(PurchaseRecord purchaseRecord) {
		this.purchaseRecord = purchaseRecord;
	}

}