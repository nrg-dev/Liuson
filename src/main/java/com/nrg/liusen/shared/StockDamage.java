package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the stock_damage database table.
 * 
 */
@Entity
@Table(name="stock_damage")
@NamedQuery(name="StockDamage.findAll", query="SELECT s FROM StockDamage s")
public class StockDamage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stock_damage_ID;

	@Column(name="daamge_time")
	private Timestamp daamgeTime;

	@Temporal(TemporalType.DATE)
	@Column(name="damage_date")
	private Date damageDate;

	@Column(name="damage_return")
	private String damageReturn;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="normal_return")
	private String normalReturn;

	private int purchase_ID;

	private String status;

	private int stock_ID;

	//bi-directional many-to-one association to Batch
	@ManyToOne
	@JoinColumn(name="batch_ID")
	private Batch batch;

	public StockDamage() {
	}

	public int getStock_damage_ID() {
		return this.stock_damage_ID;
	}

	public void setStock_damage_ID(int stock_damage_ID) {
		this.stock_damage_ID = stock_damage_ID;
	}

	public Timestamp getDaamgeTime() {
		return this.daamgeTime;
	}

	public void setDaamgeTime(Timestamp daamgeTime) {
		this.daamgeTime = daamgeTime;
	}

	public Date getDamageDate() {
		return this.damageDate;
	}

	public void setDamageDate(Date damageDate) {
		this.damageDate = damageDate;
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

	public int getPurchase_ID() {
		return this.purchase_ID;
	}

	public void setPurchase_ID(int purchase_ID) {
		this.purchase_ID = purchase_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStock_ID() {
		return this.stock_ID;
	}

	public void setStock_ID(int stock_ID) {
		this.stock_ID = stock_ID;
	}

	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

}