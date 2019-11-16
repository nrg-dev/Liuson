package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payment_ID;

	@Column(name="account_number")
	private String accountNumber;

	@Column(name="approval_status")
	private String approvalStatus;

	@Column(name="bank_name")
	private String bankName;

	@Temporal(TemporalType.DATE)
	@Column(name="cheque_date")
	private Date chequeDate;

	@Column(name="cheque_number")
	private String chequeNumber;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="paid_amount")
	private String paidAmount;

	@Temporal(TemporalType.DATE)
	@Column(name="pay_date")
	private Date payDate;

	@Column(name="pay_time")
	private Timestamp payTime;

	@Column(name="payment_status")
	private String paymentStatus;

	@Column(name="payment_type")
	private String paymentType;

	private String status;

	private String status_Director;

	private String status_FM;

	@Column(name="total_amount")
	private String totalAmount;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	@JoinColumn(name="invoice_ID")
	private Invoice invoice;

	public Payment() {
	}

	public int getPayment_ID() {
		return this.payment_ID;
	}

	public void setPayment_ID(int payment_ID) {
		this.payment_ID = payment_ID;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getChequeDate() {
		return this.chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeNumber() {
		return this.chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_Director() {
		return this.status_Director;
	}

	public void setStatus_Director(String status_Director) {
		this.status_Director = status_Director;
	}

	public String getStatus_FM() {
		return this.status_FM;
	}

	public void setStatus_FM(String status_FM) {
		this.status_FM = status_FM;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}