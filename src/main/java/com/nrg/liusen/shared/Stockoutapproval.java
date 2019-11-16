package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the stockoutapproval database table.
 * 
 */
@Entity
@NamedQuery(name="Stockoutapproval.findAll", query="SELECT s FROM Stockoutapproval s")
public class Stockoutapproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int stockoutapproval_ID;

	private String approval_login_GM;

	private String approval_login_PM;

	@Column(name="approval_status")
	private String approvalStatus;

	private String approval_status_GM;

	private String approval_status_PM;

	@Temporal(TemporalType.DATE)
	@Column(name="approved_date")
	private Date approvedDate;

	@Temporal(TemporalType.DATE)
	private Date approved_date_GM;

	@Temporal(TemporalType.DATE)
	private Date approved_date_PM;

	@Column(name="approved_login")
	private String approvedLogin;

	private String approved_reject_date_GM;

	private String approved_reject_date_PM;

	private String approved_reject_GM;

	private String approved_reject_PM;

	@Column(name="approved_reject_status")
	private String approvedRejectStatus;

	@Column(name="approved_time")
	private Timestamp approvedTime;

	@Column(name="delivery_status")
	private String deliveryStatus;

	private String reject_reason_GM;

	private String reject_reason_PM;

	//bi-directional many-to-one association to stockoutmanual
		@ManyToOne
		@JoinColumn(name="stock_out_ID")
		private StockoutManual stockoutManual;

	public Stockoutapproval() {
	}

	public int getStockoutapproval_ID() {
		return this.stockoutapproval_ID;
	}

	public void setStockoutapproval_ID(int stockoutapproval_ID) {
		this.stockoutapproval_ID = stockoutapproval_ID;
	}

	public String getApproval_login_GM() {
		return this.approval_login_GM;
	}

	public void setApproval_login_GM(String approval_login_GM) {
		this.approval_login_GM = approval_login_GM;
	}

	public String getApproval_login_PM() {
		return this.approval_login_PM;
	}

	public void setApproval_login_PM(String approval_login_PM) {
		this.approval_login_PM = approval_login_PM;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApproval_status_GM() {
		return this.approval_status_GM;
	}

	public void setApproval_status_GM(String approval_status_GM) {
		this.approval_status_GM = approval_status_GM;
	}

	public String getApproval_status_PM() {
		return this.approval_status_PM;
	}

	public void setApproval_status_PM(String approval_status_PM) {
		this.approval_status_PM = approval_status_PM;
	}

	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getApproved_date_GM() {
		return this.approved_date_GM;
	}

	public void setApproved_date_GM(Date approved_date_GM) {
		this.approved_date_GM = approved_date_GM;
	}

	public Date getApproved_date_PM() {
		return this.approved_date_PM;
	}

	public void setApproved_date_PM(Date approved_date_PM) {
		this.approved_date_PM = approved_date_PM;
	}

	public String getApprovedLogin() {
		return this.approvedLogin;
	}

	public void setApprovedLogin(String approvedLogin) {
		this.approvedLogin = approvedLogin;
	}

	public String getApproved_reject_date_GM() {
		return this.approved_reject_date_GM;
	}

	public void setApproved_reject_date_GM(String approved_reject_date_GM) {
		this.approved_reject_date_GM = approved_reject_date_GM;
	}

	public String getApproved_reject_date_PM() {
		return this.approved_reject_date_PM;
	}

	public void setApproved_reject_date_PM(String approved_reject_date_PM) {
		this.approved_reject_date_PM = approved_reject_date_PM;
	}

	public String getApproved_reject_GM() {
		return this.approved_reject_GM;
	}

	public void setApproved_reject_GM(String approved_reject_GM) {
		this.approved_reject_GM = approved_reject_GM;
	}

	public String getApproved_reject_PM() {
		return this.approved_reject_PM;
	}

	public void setApproved_reject_PM(String approved_reject_PM) {
		this.approved_reject_PM = approved_reject_PM;
	}

	public String getApprovedRejectStatus() {
		return this.approvedRejectStatus;
	}

	public void setApprovedRejectStatus(String approvedRejectStatus) {
		this.approvedRejectStatus = approvedRejectStatus;
	}

	public Timestamp getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(Timestamp approvedTime) {
		this.approvedTime = approvedTime;
	}

	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getReject_reason_GM() {
		return this.reject_reason_GM;
	}

	public void setReject_reason_GM(String reject_reason_GM) {
		this.reject_reason_GM = reject_reason_GM;
	}

	public String getReject_reason_PM() {
		return this.reject_reason_PM;
	}

	public void setReject_reason_PM(String reject_reason_PM) {
		this.reject_reason_PM = reject_reason_PM;
	}

	public StockoutManual getStockoutManual() {
		return stockoutManual;
	}

	public void setStockoutManual(StockoutManual stockoutManual) {
		this.stockoutManual = stockoutManual;
	}

}