package com.nrg.liusen.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int invoice_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="invice_date")
	private Date inviceDate;

	@Column(name="invoice_status")
	private String invoiceStatus;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="login_time")
	private Timestamp loginTime;

	//bi-directional many-to-one association to Customer
		@ManyToOne
		@JoinColumn(name="purchase_ID")
		private Purchase purchase;
		//bi-directional many-to-one association to Customer
		@ManyToOne
		@JoinColumn(name="sales_ID")
		private Sale sale;
		//bi-directional many-to-one association to Customer
		@ManyToOne
		@JoinColumn(name="stock_out_ID")
		private StockoutManual stockoutManual;
	
	private String status;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="invoice")
	private List<Payment> payments;

	public Invoice() {
	}

	public int getInvoice_ID() {
		return this.invoice_ID;
	}

	public void setInvoice_ID(int invoice_ID) {
		this.invoice_ID = invoice_ID;
	}

	public Date getInviceDate() {
		return this.inviceDate;
	}

	public void setInviceDate(Date inviceDate) {
		this.inviceDate = inviceDate;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public StockoutManual getStockoutManual() {
		return stockoutManual;
	}

	public void setStockoutManual(StockoutManual stockoutManual) {
		this.stockoutManual = stockoutManual;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setInvoice(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setInvoice(null);

		return payment;
	}

}