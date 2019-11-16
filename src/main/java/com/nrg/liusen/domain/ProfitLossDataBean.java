package com.nrg.liusen.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.shared.Sale;
import com.nrg.liusen.shared.StockoutManual;
import com.nrg.liusen.shared.Transaction;

public class ProfitLossDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6103979700200913042L;

	private Date fromDate;
	private Date toDate;
	private Date profitDate;
	private Date lossDate;
	private String profitClientName;
	private String lossClientName;
	private String profitReason;
	private String lossReason;
	private String profitAmount;
	private String lossAmount;
	private String serialno;
	
	private String totalLoss;
	private String tptalProfit;
	
	/**
	 * @return the totalLoss
	 */
	public String getTotalLoss() {
		return totalLoss;
	}
	/**
	 * @param totalLoss the totalLoss to set
	 */
	public void setTotalLoss(String totalLoss) {
		this.totalLoss = totalLoss;
	}
	/**
	 * @return the tptalProfit
	 */
	public String getTptalProfit() {
		return tptalProfit;
	}
	/**
	 * @param tptalProfit the tptalProfit to set
	 */
	public void setTptalProfit(String tptalProfit) {
		this.tptalProfit = tptalProfit;
	}
	private List<Sale> salesList=null;
	
	private List<Payroll> payList=null;
			
	private List<Purchase> purchaseList=null;	
	
	private List<Transaction> transactionList=null;	
	
	private List<Transaction> transactionList1=null;	
	
	private List<StockoutManual> StockoutManualList=null;	
	
	
	
	/**
	 * @return the stockoutManualList
	 */
	public List<StockoutManual> getStockoutManualList() {
		return StockoutManualList;
	}
	/**
	 * @param stockoutManualList the stockoutManualList to set
	 */
	public void setStockoutManualList(List<StockoutManual> stockoutManualList) {
		StockoutManualList = stockoutManualList;
	}
	/**
	 * @return the payList
	 */
	public List<Payroll> getPayList() {
		return payList;
	}
	/**
	 * @param payList the payList to set
	 */
	public void setPayList(List<Payroll> payList) {
		this.payList = payList;
	}
	/**
	 * @return the purchaseList
	 */
	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}
	/**
	 * @param purchaseList the purchaseList to set
	 */
	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}
	/**
	 * @return the transactionList
	 */
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	/**
	 * @return the transactionList1
	 */
	public List<Transaction> getTransactionList1() {
		return transactionList1;
	}
	/**
	 * @param transactionList1 the transactionList1 to set
	 */
	public void setTransactionList1(List<Transaction> transactionList1) {
		this.transactionList1 = transactionList1;
	}
	/**
	 * @return the salesList
	 */
	public List<Sale> getSalesList() {
		return salesList;
	}
	/**
	 * @param salesList the salesList to set
	 */
	public void setSalesList(List<Sale> salesList) {
		this.salesList = salesList;
	}
	/**
	 * @return the profitDate
	 */
	public Date getProfitDate() {
		return profitDate;
	}
	/**
	 * @param profitDate the profitDate to set
	 */
	public void setProfitDate(Date profitDate) {
		this.profitDate = profitDate;
	}
	/**
	 * @return the lossDate
	 */
	public Date getLossDate() {
		return lossDate;
	}
	/**
	 * @param lossDate the lossDate to set
	 */
	public void setLossDate(Date lossDate) {
		this.lossDate = lossDate;
	}
	/**
	 * @return the profitClientName
	 */
	public String getProfitClientName() {
		return profitClientName;
	}
	/**
	 * @param profitClientName the profitClientName to set
	 */
	public void setProfitClientName(String profitClientName) {
		this.profitClientName = profitClientName;
	}
	/**
	 * @return the lossClientName
	 */
	public String getLossClientName() {
		return lossClientName;
	}
	/**
	 * @param lossClientName the lossClientName to set
	 */
	public void setLossClientName(String lossClientName) {
		this.lossClientName = lossClientName;
	}
	/**
	 * @return the profitReason
	 */
	public String getProfitReason() {
		return profitReason;
	}
	/**
	 * @param profitReason the profitReason to set
	 */
	public void setProfitReason(String profitReason) {
		this.profitReason = profitReason;
	}
	/**
	 * @return the lossReason
	 */
	public String getLossReason() {
		return lossReason;
	}
	/**
	 * @param lossReason the lossReason to set
	 */
	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}
	/**
	 * @return the profitAmount
	 */
	public String getProfitAmount() {
		return profitAmount;
	}
	/**
	 * @param profitAmount the profitAmount to set
	 */
	public void setProfitAmount(String profitAmount) {
		this.profitAmount = profitAmount;
	}
	/**
	 * @return the lossAmount
	 */
	public String getLossAmount() {
		return lossAmount;
	}
	/**
	 * @param lossAmount the lossAmount to set
	 */
	public void setLossAmount(String lossAmount) {
		this.lossAmount = lossAmount;
	}
	/**
	 * @return the serialno
	 */
	public String getSerialno() {
		return serialno;
	}
	/**
	 * @param serialno the serialno to set
	 */
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
