package com.nrg.liusen.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


/**
 * The persistent class for the projet database table.
 * 
 */
@Entity
@NamedQuery(name="Projet.findAll", query="SELECT p FROM Projet p")
public class Projet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int project_ID;

	private String address;

	@Column(name="client_name")
	private String clientName;

	@Column(name="contact_number")
	private String contactNumber;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="file_path")
	private String filePath;

	private String location;

	@Column(name="login_status")
	private String loginStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="project_date1")
	private Date projectDate1;

	@Temporal(TemporalType.DATE)
	@Column(name="project_date2")
	private Date projectDate2;

	@Temporal(TemporalType.DATE)
	@Column(name="project_date3")
	private Date projectDate3;

	@Column(name="project_name")
	private String projectName;

	@Temporal(TemporalType.DATE)
	@Column(name="project_reg_date")
	private Date projectRegDate;

	@Column(name="project_reg_time")
	private Timestamp projectRegTime;

	@Column(name="project_value")
	private String projectValue;

	private String state;

	private String status;
	
	//bi-directional many-to-one association to StockoutManual
		@OneToMany(mappedBy="projet")
		private List<StockoutManual> stockoutManuals;


	public Projet() {
	}

	public int getProject_ID() {
		return this.project_ID;
	}

	public void setProject_ID(int project_ID) {
		this.project_ID = project_ID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Date getProjectDate1() {
		return this.projectDate1;
	}

	public void setProjectDate1(Date projectDate1) {
		this.projectDate1 = projectDate1;
	}

	public Date getProjectDate2() {
		return this.projectDate2;
	}

	public void setProjectDate2(Date projectDate2) {
		this.projectDate2 = projectDate2;
	}

	public Date getProjectDate3() {
		return this.projectDate3;
	}

	public void setProjectDate3(Date projectDate3) {
		this.projectDate3 = projectDate3;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getProjectRegDate() {
		return this.projectRegDate;
	}

	public void setProjectRegDate(Date projectRegDate) {
		this.projectRegDate = projectRegDate;
	}

	public Timestamp getProjectRegTime() {
		return this.projectRegTime;
	}

	public void setProjectRegTime(Timestamp projectRegTime) {
		this.projectRegTime = projectRegTime;
	}

	public String getProjectValue() {
		return this.projectValue;
	}

	public void setProjectValue(String projectValue) {
		this.projectValue = projectValue;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public List<StockoutManual> getStockoutManuals() {
		return this.stockoutManuals;
	}

	public void setStockoutManuals(List<StockoutManual> stockoutManuals) {
		this.stockoutManuals = stockoutManuals;
	}

	public StockoutManual addStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().add(stockoutManual);
		stockoutManual.setProjet(this);

		return stockoutManual;
	}

	public StockoutManual removeStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().remove(stockoutManual);
		stockoutManual.setProjet(null);

		return stockoutManual;
	}
}