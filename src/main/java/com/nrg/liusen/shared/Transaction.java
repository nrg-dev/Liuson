package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int transaction_ID;

	@Column(name="account_number")
	private String accountNumber;

	@Column(name="bank_name")
	private String bankName;

	@Temporal(TemporalType.DATE)
	@Column(name="cheque_date")
	private Date chequeDate;

	@Column(name="cheque_no")
	private String chequeNo;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;
	
	private String transactionNo;
	
	@Column(name="edit_status")
	private String editStatus;

	@Column(name="edit_time")
	private Timestamp editTime;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="payment_mode")
	private String paymentMode;

	private String status;
	
	@Temporal(TemporalType.DATE)
	@Column(name="trans_date")
	private Date transDate;

	@Column(name="trans_time")
	private Timestamp transTime;

	@Column(name="transaction_amount")
	private String transactionAmount;

	@Temporal(TemporalType.DATE)
	@Column(name="transaction_date")
	private Date transactionDate;

	@Column(name="transaction_name")
	private String transactionName;

	@Column(name="transaction_note")
	private String transactionNote;

	@Column(name="transaction_type")
	private String transactionType;

	public Transaction() {
	}

	public int getTransaction_ID() {
		return this.transaction_ID;
	}

	public void setTransaction_ID(int transaction_ID) {
		this.transaction_ID = transaction_ID;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getChequeNo() {
		return this.chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getEditLoginStatus() {
		return this.editLoginStatus;
	}

	public void setEditLoginStatus(String editLoginStatus) {
		this.editLoginStatus = editLoginStatus;
	}

	public String getEditStatus() {
		return this.editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Timestamp getTransTime() {
		return this.transTime;
	}

	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}

	public String getTransactionAmount() {
		return this.transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionName() {
		return this.transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getTransactionNote() {
		return this.transactionNote;
	}

	public void setTransactionNote(String transactionNote) {
		this.transactionNote = transactionNote;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

}