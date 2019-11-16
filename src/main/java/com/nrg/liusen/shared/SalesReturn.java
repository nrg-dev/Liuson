package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the sales_return database table.
 * 
 */
@Entity
@Table(name="sales_return")
@NamedQuery(name="SalesReturn.findAll", query="SELECT s FROM SalesReturn s")
public class SalesReturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_return_ID;

	@Column(name="damage_return")
	private String damageReturn;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="normal_return")
	private String normalReturn;

	@Temporal(TemporalType.DATE)
	@Column(name="sales_return_date")
	private Date salesReturnDate;

	@Column(name="sales_return_time")
	private Timestamp salesReturnTime;

	private String status;

	//bi-directional many-to-one association to SalesRecord
	@ManyToOne
	@JoinColumn(name="sales_record_ID")
	private SalesRecord salesRecord;

	public SalesReturn() {
	}

	public int getSales_return_ID() {
		return this.sales_return_ID;
	}

	public void setSales_return_ID(int sales_return_ID) {
		this.sales_return_ID = sales_return_ID;
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

	public Date getSalesReturnDate() {
		return this.salesReturnDate;
	}

	public void setSalesReturnDate(Date salesReturnDate) {
		this.salesReturnDate = salesReturnDate;
	}

	public Timestamp getSalesReturnTime() {
		return this.salesReturnTime;
	}

	public void setSalesReturnTime(Timestamp salesReturnTime) {
		this.salesReturnTime = salesReturnTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SalesRecord getSalesRecord() {
		return this.salesRecord;
	}

	public void setSalesRecord(SalesRecord salesRecord) {
		this.salesRecord = salesRecord;
	}

}