package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the product_limit database table.
 * 
 */
@Entity
@Table(name="product_limit")
@NamedQuery(name="ProductLimit.findAll", query="SELECT p FROM ProductLimit p")
public class ProductLimit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int limit_ID;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login")
	private String editLogin;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="edit_time")
	private Timestamp editTime;

	@Column(name="limit_size")
	private String limitSize;

	@Column(name="login_status")
	private String loginStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="reg_date")
	private Date regDate;

	@Column(name="reg_time")
	private Timestamp regTime;

	private String status;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to RawMaterial
	@ManyToOne
	@JoinColumn(name="raw_id")
	private RawMaterial rawMaterial;

	public ProductLimit() {
	}

	public int getLimit_ID() {
		return this.limit_ID;
	}

	public void setLimit_ID(int limit_ID) {
		this.limit_ID = limit_ID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getEditLogin() {
		return this.editLogin;
	}

	public void setEditLogin(String editLogin) {
		this.editLogin = editLogin;
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

	public String getLimitSize() {
		return this.limitSize;
	}

	public void setLimitSize(String limitSize) {
		this.limitSize = limitSize;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Timestamp getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public RawMaterial getRawMaterial() {
		return this.rawMaterial;
	}

	public void setRawMaterial(RawMaterial rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

}