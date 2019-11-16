package com.nrg.liusen.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the sales_approval database table.
 * 
 */
@Entity
@Table(name="sales_approval")
@NamedQuery(name="SalesApproval.findAll", query="SELECT s FROM SalesApproval s")
public class SalesApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_approval_ID;

	private String approval_login_GM;

	private String approval_login_MM;

	@Column(name="approval_status")
	private String approvalStatus;

	private String approval_status_GM;

	private String approval_status_MM;
	private String reject_reason_MM;
	private String reject_reason_GM;
	private String reject_reason_PM;

	public String getReject_reason_PM() {
		return reject_reason_PM;
	}

	public void setReject_reason_PM(String reject_reason_PM) {
		this.reject_reason_PM = reject_reason_PM;
	}

	public String getReject_reason_MM() {
		return reject_reason_MM;
	}

	public void setReject_reason_MM(String reject_reason_MM) {
		this.reject_reason_MM = reject_reason_MM;
	}

	public String getReject_reason_GM() {
		return reject_reason_GM;
	}

	public void setReject_reason_GM(String reject_reason_GM) {
		this.reject_reason_GM = reject_reason_GM;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="approved_date")
	private Date approvedDate;

	@Temporal(TemporalType.DATE)
	private Date approved_date_GM;

	@Temporal(TemporalType.DATE)
	private Date approved_date_MM;

	@Column(name="approved_login")
	private String approvedLogin;

	private String approved_reject_date_GM;

	private String approved_reject_date_MM;

	private String approved_reject_GM;

	private String approved_reject_MM;

	@Column(name="approved_reject_status")
	private String approvedRejectStatus;

	@Column(name="approved_time")
	private Timestamp approvedTime;

	@Temporal(TemporalType.DATE)
	@Column(name="delivery_date")
	private Date deliveryDate;

	@Temporal(TemporalType.DATE)
	private Date delivery_date_GM;

	@Temporal(TemporalType.DATE)
	private Date delivery_date_PM;

	private String delivery_login_GM;

	private String delivery_login_PM;

	@Column(name="delivery_login_status")
	private String deliveryLoginStatus;

	private String delivery_reject_GM;

	@Temporal(TemporalType.DATE)
	private Date delivery_reject_GMdate;

	private String delivery_reject_PM;

	@Temporal(TemporalType.DATE)
	private Date delivery_reject_PMdate;

	@Column(name="delivery_reject_status")
	private String deliveryRejectStatus;

	@Column(name="delivery_status")
	private String deliveryStatus;

	private String delivery_status_GM;

	private String delivery_status_PM;

	@Column(name="delivery_time")
	private Timestamp deliveryTime;

	public SalesApproval() {
	}

	public int getSales_approval_ID() {
		return this.sales_approval_ID;
	}

	public void setSales_approval_ID(int sales_approval_ID) {
		this.sales_approval_ID = sales_approval_ID;
	}

	public String getApproval_login_GM() {
		return this.approval_login_GM;
	}

	public void setApproval_login_GM(String approval_login_GM) {
		this.approval_login_GM = approval_login_GM;
	}

	public String getApproval_login_MM() {
		return this.approval_login_MM;
	}

	public void setApproval_login_MM(String approval_login_MM) {
		this.approval_login_MM = approval_login_MM;
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

	public String getApproval_status_MM() {
		return this.approval_status_MM;
	}

	public void setApproval_status_MM(String approval_status_MM) {
		this.approval_status_MM = approval_status_MM;
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

	public Date getApproved_date_MM() {
		return this.approved_date_MM;
	}

	public void setApproved_date_MM(Date approved_date_MM) {
		this.approved_date_MM = approved_date_MM;
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

	public String getApproved_reject_date_MM() {
		return this.approved_reject_date_MM;
	}

	public void setApproved_reject_date_MM(String approved_reject_date_MM) {
		this.approved_reject_date_MM = approved_reject_date_MM;
	}

	public String getApproved_reject_GM() {
		return this.approved_reject_GM;
	}

	public void setApproved_reject_GM(String approved_reject_GM) {
		this.approved_reject_GM = approved_reject_GM;
	}

	public String getApproved_reject_MM() {
		return this.approved_reject_MM;
	}

	public void setApproved_reject_MM(String approved_reject_MM) {
		this.approved_reject_MM = approved_reject_MM;
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

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDelivery_date_GM() {
		return this.delivery_date_GM;
	}

	public void setDelivery_date_GM(Date delivery_date_GM) {
		this.delivery_date_GM = delivery_date_GM;
	}

	public Date getDelivery_date_PM() {
		return this.delivery_date_PM;
	}

	public void setDelivery_date_PM(Date delivery_date_PM) {
		this.delivery_date_PM = delivery_date_PM;
	}

	public String getDelivery_login_GM() {
		return this.delivery_login_GM;
	}

	public void setDelivery_login_GM(String delivery_login_GM) {
		this.delivery_login_GM = delivery_login_GM;
	}

	public String getDelivery_login_PM() {
		return this.delivery_login_PM;
	}

	public void setDelivery_login_PM(String delivery_login_PM) {
		this.delivery_login_PM = delivery_login_PM;
	}

	public String getDeliveryLoginStatus() {
		return this.deliveryLoginStatus;
	}

	public void setDeliveryLoginStatus(String deliveryLoginStatus) {
		this.deliveryLoginStatus = deliveryLoginStatus;
	}

	public String getDelivery_reject_GM() {
		return this.delivery_reject_GM;
	}

	public void setDelivery_reject_GM(String delivery_reject_GM) {
		this.delivery_reject_GM = delivery_reject_GM;
	}

	public Date getDelivery_reject_GMdate() {
		return this.delivery_reject_GMdate;
	}

	public void setDelivery_reject_GMdate(Date delivery_reject_GMdate) {
		this.delivery_reject_GMdate = delivery_reject_GMdate;
	}

	public String getDelivery_reject_PM() {
		return this.delivery_reject_PM;
	}

	public void setDelivery_reject_PM(String delivery_reject_PM) {
		this.delivery_reject_PM = delivery_reject_PM;
	}

	public Date getDelivery_reject_PMdate() {
		return this.delivery_reject_PMdate;
	}

	public void setDelivery_reject_PMdate(Date delivery_reject_PMdate) {
		this.delivery_reject_PMdate = delivery_reject_PMdate;
	}

	public String getDeliveryRejectStatus() {
		return this.deliveryRejectStatus;
	}

	public void setDeliveryRejectStatus(String deliveryRejectStatus) {
		this.deliveryRejectStatus = deliveryRejectStatus;
	}

	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDelivery_status_GM() {
		return this.delivery_status_GM;
	}

	public void setDelivery_status_GM(String delivery_status_GM) {
		this.delivery_status_GM = delivery_status_GM;
	}

	public String getDelivery_status_PM() {
		return this.delivery_status_PM;
	}

	public void setDelivery_status_PM(String delivery_status_PM) {
		this.delivery_status_PM = delivery_status_PM;
	}

	public Timestamp getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	//bi-directional many-to-one association to sales
			@ManyToOne
			@JoinColumn(name="Sales_ID")
			private Sale sales;

			public Sale getSales() {
				return sales;
			}

			public void setSales(Sale sales) {
				this.sales = sales;
			}

}