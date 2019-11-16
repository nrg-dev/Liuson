package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the purchase_approval database table.
 * 
 */
@Entity
@Table(name="purchase_approval")
@NamedQuery(name="PurchaseApproval.findAll", query="SELECT p FROM PurchaseApproval p")
public class PurchaseApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int purchase_approval_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_approval_date")
	private Date purchaseApprovalDate;

	@Temporal(TemporalType.DATE)
	private Date purchase_approval_dateGM;

	@Temporal(TemporalType.DATE)
	private Date purchase_approval_dateMM;

	private String purchase_approval_GMlogin;

	private String purchase_approval_GMstatus;

	private String purchase_approval_MMlogin;

	private String purchase_approval_MMstatus;

	private String purchase_approval_rejectGM;

	private String purchase_approval_rejectMM;

	private String purchase_approval_rejectStatus;

	@Column(name="purchase_approval_status")
	private String purchaseApprovalStatus;

	@Column(name="purchase_approval_time")
	private Timestamp purchaseApprovalTime;

	@Temporal(TemporalType.DATE)
	@Column(name="purchase_delivery_date")
	private Date purchaseDeliveryDate;

	@Temporal(TemporalType.DATE)
	private Date purchase_delivery_dateGM;

	@Temporal(TemporalType.DATE)
	private Date purchase_delivery_datePM;

	private String purchase_delivery_GMlogin;

	private String purchase_delivery_GMstatus;

	private String purchase_delivery_PMlogin;

	private String purchase_delivery_PMstatus;

	private String purchase_delivery_rejectGM;

	private String purchase_delivery_rejectPM;

	@Column(name="purchase_delivery_rejectstatus")
	private String purchaseDeliveryRejectstatus;

	@Column(name="purchase_delivery_status")
	private String purchaseDeliveryStatus;

	@Column(name="purchase_delivery_time")
	private Timestamp purchaseDeliveryTime;

	private String status;

	private String status2;

	//bi-directional many-to-one association to Purchase
	@ManyToOne
	@JoinColumn(name="purchase_ID")
	private Purchase purchase;

	public PurchaseApproval() {
	}

	public int getPurchase_approval_ID() {
		return this.purchase_approval_ID;
	}

	public void setPurchase_approval_ID(int purchase_approval_ID) {
		this.purchase_approval_ID = purchase_approval_ID;
	}

	public Date getPurchaseApprovalDate() {
		return this.purchaseApprovalDate;
	}

	public void setPurchaseApprovalDate(Date purchaseApprovalDate) {
		this.purchaseApprovalDate = purchaseApprovalDate;
	}

	public Date getPurchase_approval_dateGM() {
		return this.purchase_approval_dateGM;
	}

	public void setPurchase_approval_dateGM(Date purchase_approval_dateGM) {
		this.purchase_approval_dateGM = purchase_approval_dateGM;
	}

	public Date getPurchase_approval_dateMM() {
		return this.purchase_approval_dateMM;
	}

	public void setPurchase_approval_dateMM(Date purchase_approval_dateMM) {
		this.purchase_approval_dateMM = purchase_approval_dateMM;
	}

	public String getPurchase_approval_GMlogin() {
		return this.purchase_approval_GMlogin;
	}

	public void setPurchase_approval_GMlogin(String purchase_approval_GMlogin) {
		this.purchase_approval_GMlogin = purchase_approval_GMlogin;
	}

	public String getPurchase_approval_GMstatus() {
		return this.purchase_approval_GMstatus;
	}

	public void setPurchase_approval_GMstatus(String purchase_approval_GMstatus) {
		this.purchase_approval_GMstatus = purchase_approval_GMstatus;
	}

	public String getPurchase_approval_MMlogin() {
		return this.purchase_approval_MMlogin;
	}

	public void setPurchase_approval_MMlogin(String purchase_approval_MMlogin) {
		this.purchase_approval_MMlogin = purchase_approval_MMlogin;
	}

	public String getPurchase_approval_MMstatus() {
		return this.purchase_approval_MMstatus;
	}

	public void setPurchase_approval_MMstatus(String purchase_approval_MMstatus) {
		this.purchase_approval_MMstatus = purchase_approval_MMstatus;
	}

	public String getPurchase_approval_rejectGM() {
		return this.purchase_approval_rejectGM;
	}

	public void setPurchase_approval_rejectGM(String purchase_approval_rejectGM) {
		this.purchase_approval_rejectGM = purchase_approval_rejectGM;
	}

	public String getPurchase_approval_rejectMM() {
		return this.purchase_approval_rejectMM;
	}

	public void setPurchase_approval_rejectMM(String purchase_approval_rejectMM) {
		this.purchase_approval_rejectMM = purchase_approval_rejectMM;
	}

	public String getPurchase_approval_rejectStatus() {
		return this.purchase_approval_rejectStatus;
	}

	public void setPurchase_approval_rejectStatus(String purchase_approval_rejectStatus) {
		this.purchase_approval_rejectStatus = purchase_approval_rejectStatus;
	}

	public String getPurchaseApprovalStatus() {
		return this.purchaseApprovalStatus;
	}

	public void setPurchaseApprovalStatus(String purchaseApprovalStatus) {
		this.purchaseApprovalStatus = purchaseApprovalStatus;
	}

	public Timestamp getPurchaseApprovalTime() {
		return this.purchaseApprovalTime;
	}

	public void setPurchaseApprovalTime(Timestamp purchaseApprovalTime) {
		this.purchaseApprovalTime = purchaseApprovalTime;
	}

	public Date getPurchaseDeliveryDate() {
		return this.purchaseDeliveryDate;
	}

	public void setPurchaseDeliveryDate(Date purchaseDeliveryDate) {
		this.purchaseDeliveryDate = purchaseDeliveryDate;
	}

	public Date getPurchase_delivery_dateGM() {
		return this.purchase_delivery_dateGM;
	}

	public void setPurchase_delivery_dateGM(Date purchase_delivery_dateGM) {
		this.purchase_delivery_dateGM = purchase_delivery_dateGM;
	}

	public Date getPurchase_delivery_datePM() {
		return this.purchase_delivery_datePM;
	}

	public void setPurchase_delivery_datePM(Date purchase_delivery_datePM) {
		this.purchase_delivery_datePM = purchase_delivery_datePM;
	}

	public String getPurchase_delivery_GMlogin() {
		return this.purchase_delivery_GMlogin;
	}

	public void setPurchase_delivery_GMlogin(String purchase_delivery_GMlogin) {
		this.purchase_delivery_GMlogin = purchase_delivery_GMlogin;
	}

	public String getPurchase_delivery_GMstatus() {
		return this.purchase_delivery_GMstatus;
	}

	public void setPurchase_delivery_GMstatus(String purchase_delivery_GMstatus) {
		this.purchase_delivery_GMstatus = purchase_delivery_GMstatus;
	}

	public String getPurchase_delivery_PMlogin() {
		return this.purchase_delivery_PMlogin;
	}

	public void setPurchase_delivery_PMlogin(String purchase_delivery_PMlogin) {
		this.purchase_delivery_PMlogin = purchase_delivery_PMlogin;
	}

	public String getPurchase_delivery_PMstatus() {
		return this.purchase_delivery_PMstatus;
	}

	public void setPurchase_delivery_PMstatus(String purchase_delivery_PMstatus) {
		this.purchase_delivery_PMstatus = purchase_delivery_PMstatus;
	}

	public String getPurchase_delivery_rejectGM() {
		return this.purchase_delivery_rejectGM;
	}

	public void setPurchase_delivery_rejectGM(String purchase_delivery_rejectGM) {
		this.purchase_delivery_rejectGM = purchase_delivery_rejectGM;
	}

	public String getPurchase_delivery_rejectPM() {
		return this.purchase_delivery_rejectPM;
	}

	public void setPurchase_delivery_rejectPM(String purchase_delivery_rejectPM) {
		this.purchase_delivery_rejectPM = purchase_delivery_rejectPM;
	}

	public String getPurchaseDeliveryRejectstatus() {
		return this.purchaseDeliveryRejectstatus;
	}

	public void setPurchaseDeliveryRejectstatus(String purchaseDeliveryRejectstatus) {
		this.purchaseDeliveryRejectstatus = purchaseDeliveryRejectstatus;
	}

	public String getPurchaseDeliveryStatus() {
		return this.purchaseDeliveryStatus;
	}

	public void setPurchaseDeliveryStatus(String purchaseDeliveryStatus) {
		this.purchaseDeliveryStatus = purchaseDeliveryStatus;
	}

	public Timestamp getPurchaseDeliveryTime() {
		return this.purchaseDeliveryTime;
	}

	public void setPurchaseDeliveryTime(Timestamp purchaseDeliveryTime) {
		this.purchaseDeliveryTime = purchaseDeliveryTime;
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

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

}