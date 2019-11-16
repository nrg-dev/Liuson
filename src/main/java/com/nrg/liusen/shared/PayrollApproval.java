package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the payroll_approval database table.
 * 
 */
@Entity
@Table(name="payroll_approval")
@NamedQuery(name="PayrollApproval.findAll", query="SELECT p FROM PayrollApproval p")
public class PayrollApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payroll_approval_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="app_date")
	private Date appDate;

	@Column(name="app_time")
	private Timestamp appTime;

	private String approved_status_Director;

	private String approved_status_FM;

	@Column(name="login_status")
	private String loginStatus;

	private String login_status_Director;

	private String login_status_FM;

	private String reject_Director_status;

	private String reject_FM_status;

	@Column(name="reject_reason")
	private String rejectReason;

	@Column(name="reject_status")
	private String rejectStatus;

	private String status;

	private String status2;

	//bi-directional many-to-one association to Payroll
	@ManyToOne
	@JoinColumn(name="payroll_ID")
	private Payroll payroll;

	public PayrollApproval() {
	}

	public int getPayroll_approval_ID() {
		return this.payroll_approval_ID;
	}

	public void setPayroll_approval_ID(int payroll_approval_ID) {
		this.payroll_approval_ID = payroll_approval_ID;
	}

	public Date getAppDate() {
		return this.appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Timestamp getAppTime() {
		return this.appTime;
	}

	public void setAppTime(Timestamp appTime) {
		this.appTime = appTime;
	}

	public String getApproved_status_Director() {
		return this.approved_status_Director;
	}

	public void setApproved_status_Director(String approved_status_Director) {
		this.approved_status_Director = approved_status_Director;
	}

	public String getApproved_status_FM() {
		return this.approved_status_FM;
	}

	public void setApproved_status_FM(String approved_status_FM) {
		this.approved_status_FM = approved_status_FM;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLogin_status_Director() {
		return this.login_status_Director;
	}

	public void setLogin_status_Director(String login_status_Director) {
		this.login_status_Director = login_status_Director;
	}

	public String getLogin_status_FM() {
		return this.login_status_FM;
	}

	public void setLogin_status_FM(String login_status_FM) {
		this.login_status_FM = login_status_FM;
	}

	public String getReject_Director_status() {
		return this.reject_Director_status;
	}

	public void setReject_Director_status(String reject_Director_status) {
		this.reject_Director_status = reject_Director_status;
	}

	public String getReject_FM_status() {
		return this.reject_FM_status;
	}

	public void setReject_FM_status(String reject_FM_status) {
		this.reject_FM_status = reject_FM_status;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRejectStatus() {
		return this.rejectStatus;
	}

	public void setRejectStatus(String rejectStatus) {
		this.rejectStatus = rejectStatus;
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

	public Payroll getPayroll() {
		return this.payroll;
	}

	public void setPayroll(Payroll payroll) {
		this.payroll = payroll;
	}

}