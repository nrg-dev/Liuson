package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;

import com.nrg.liusen.shared.Transaction;

public class TransactionDataBean {

	private String transName;
	private Date transDate;
	private String transAmmount;
	private String transPaymentType;
	private String transTransactionType;
	private String transNote;
	private String transCardBankName;
	private String transCardAccountNo;
	private String transChequeBName;
	private String transChequeNo;
	private Date transChequeDate;
	private String transTransferBName;
	private String transtranferAccontNo;
	private Date transFromDate;
	private Date transToDate;
	private String transno;
	private String transstatus;
	
	
    public String getTransstatus() {
		return transstatus;
	}
	public void setTransstatus(String transstatus) {
		this.transstatus = transstatus;
	}
	public String getTransno() {
		return transno;
	}
	public void setTransno(String transno) {
		this.transno = transno;
	}
	public List<Transaction>translist=null;

    
	
	
	public List<Transaction> getTranslist() {
	return translist;
}
    public void setTranslist(List<Transaction> translist) {
	this.translist = translist;
}
 

	/**
	 * @return the transFromDate
	 */
	public Date getTransFromDate() {
		return transFromDate;
	}
	/**
	 * @param transFromDate the transFromDate to set
	 */
	public void setTransFromDate(Date transFromDate) {
		this.transFromDate = transFromDate;
	}
	/**
	 * @return the transToDate
	 */
	public Date getTransToDate() {
		return transToDate;
	}
	/**
	 * @param transToDate the transToDate to set
	 */
	public void setTransToDate(Date transToDate) {
		this.transToDate = transToDate;
	}
	/**
	 * @return the transName
	 */
	public String getTransName() {
		return transName;
	}
	/**
	 * @param transName the transName to set
	 */
	public void setTransName(String transName) {
		this.transName = transName;
	}
	/**
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}
	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	/**
	 * @return the transAmmount
	 */
	public String getTransAmmount() {
		return transAmmount;
	}
	/**
	 * @param transAmmount the transAmmount to set
	 */
	public void setTransAmmount(String transAmmount) {
		this.transAmmount = transAmmount;
	}
	/**
	 * @return the transPaymentType
	 */
	public String getTransPaymentType() {
		return transPaymentType;
	}
	/**
	 * @param transPaymentType the transPaymentType to set
	 */
	public void setTransPaymentType(String transPaymentType) {
		this.transPaymentType = transPaymentType;
	}
	/**
	 * @return the transTransactionType
	 */
	public String getTransTransactionType() {
		return transTransactionType;
	}
	/**
	 * @param transTransactionType the transTransactionType to set
	 */
	public void setTransTransactionType(String transTransactionType) {
		this.transTransactionType = transTransactionType;
	}
	/**
	 * @return the transNote
	 */
	public String getTransNote() {
		return transNote;
	}
	/**
	 * @param transNote the transNote to set
	 */
	public void setTransNote(String transNote) {
		this.transNote = transNote;
	}
	/**
	 * @return the transCardBankName
	 */
	public String getTransCardBankName() {
		return transCardBankName;
	}
	/**
	 * @param transCardBankName the transCardBankName to set
	 */
	public void setTransCardBankName(String transCardBankName) {
		this.transCardBankName = transCardBankName;
	}
	/**
	 * @return the transCardAccountNo
	 */
	public String getTransCardAccountNo() {
		return transCardAccountNo;
	}
	/**
	 * @param transCardAccountNo the transCardAccountNo to set
	 */
	public void setTransCardAccountNo(String transCardAccountNo) {
		this.transCardAccountNo = transCardAccountNo;
	}
	/**
	 * @return the transChequeBName
	 */
	public String getTransChequeBName() {
		return transChequeBName;
	}
	/**
	 * @param transChequeBName the transChequeBName to set
	 */
	public void setTransChequeBName(String transChequeBName) {
		this.transChequeBName = transChequeBName;
	}
	/**
	 * @return the transChequeNo
	 */
	public String getTransChequeNo() {
		return transChequeNo;
	}
	/**
	 * @param transChequeNo the transChequeNo to set
	 */
	public void setTransChequeNo(String transChequeNo) {
		this.transChequeNo = transChequeNo;
	}
	/**
	 * @return the transChequeDate
	 */
	public Date getTransChequeDate() {
		return transChequeDate;
	}
	/**
	 * @param transChequeDate the transChequeDate to set
	 */
	public void setTransChequeDate(Date transChequeDate) {
		this.transChequeDate = transChequeDate;
	}
	/**
	 * @return the transTransferBName
	 */
	public String getTransTransferBName() {

		return transTransferBName;
	}
	/**
	 * @param transTransferBName the transTransferBName to set
	 */
	public void setTransTransferBName(String transTransferBName) {
		this.transTransferBName = transTransferBName;
	}
	/**
	 * @return the transtranferAccontNo
	 */
	public String getTranstranferAccontNo() {
		
		return transtranferAccontNo;
		
	}
	/**
	 * @param transtranferAccontNo the transtranferAccontNo to set
	 */
	public void setTranstranferAccontNo(String transtranferAccontNo) {
		this.transtranferAccontNo = transtranferAccontNo;
	}
	
}
