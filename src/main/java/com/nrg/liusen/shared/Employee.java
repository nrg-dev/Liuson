package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int employee_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="emp_date")
	private Date empDate;

	@Column(name="emp_time")
	private Timestamp empTime;

	@Column(name="employee_address")
	private String employeeAddress;

	@Column(name="employee_basic_salary")
	private String employeeBasicSalary;

	@Column(name="employee_description")
	private String employeeDescription;

	private String employee_details_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="employee_dob")
	private Date employeeDob;

	@Temporal(TemporalType.DATE)
	@Column(name="employee_edit_date")
	private Date employeeEditDate;

	@Column(name="employee_edit_login")
	private String employeeEditLogin;

	@Column(name="employee_edit_status")
	private String employeeEditStatus;

	@Column(name="employee_email")
	private String employeeEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="employee_entry_date")
	private Date employeeEntryDate;

	@Column(name="employee_gender")
	private String employeeGender;

	@Temporal(TemporalType.DATE)
	@Column(name="employee_joint_date")
	private Date employeeJointDate;

	@Column(name="employee_login_status")
	private String employeeLoginStatus;

	@Column(name="employee_name")
	private String employeeName;

	private String empPhone;

	private String status;

	//bi-directional many-to-one association to Customer
	@OneToMany(mappedBy="employee")
	private List<Customer> customers;

	//bi-directional many-to-one association to Designation
	@ManyToOne
	@JoinColumn(name="employee_designation")
	private Designation designation;

	//bi-directional many-to-one association to Qualification
	@ManyToOne
	@JoinColumn(name="employee_qualification")
	private Qualification qualification;

	//bi-directional many-to-one association to Payroll
	@OneToMany(mappedBy="employee")
	private List<Payroll> payrolls;

	//bi-directional many-to-one association to ProjectEmployee
	@OneToMany(mappedBy="employee")
	private List<ProjectEmployee> projectEmployees;

	//bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy="employee")
	private List<Purchase> purchases;

	//bi-directional many-to-one association to Sale
	@OneToMany(mappedBy="employee")
	private List<Sale> sales;

	//bi-directional many-to-one association to StockoutManual
	@OneToMany(mappedBy="employee")
	private List<StockoutManual> stockoutManuals;

	public Employee() {
	}

	public int getEmployee_ID() {
		return this.employee_ID;
	}

	public void setEmployee_ID(int employee_ID) {
		this.employee_ID = employee_ID;
	}

	public Date getEmpDate() {
		return this.empDate;
	}

	public void setEmpDate(Date empDate) {
		this.empDate = empDate;
	}

	public Timestamp getEmpTime() {
		return this.empTime;
	}

	public void setEmpTime(Timestamp empTime) {
		this.empTime = empTime;
	}

	public String getEmployeeAddress() {
		return this.employeeAddress;
	}

	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public String getEmployeeBasicSalary() {
		return this.employeeBasicSalary;
	}

	public void setEmployeeBasicSalary(String employeeBasicSalary) {
		this.employeeBasicSalary = employeeBasicSalary;
	}

	public String getEmployeeDescription() {
		return this.employeeDescription;
	}

	public void setEmployeeDescription(String employeeDescription) {
		this.employeeDescription = employeeDescription;
	}

	public String getEmployee_details_ID() {
		return this.employee_details_ID;
	}

	public void setEmployee_details_ID(String employee_details_ID) {
		this.employee_details_ID = employee_details_ID;
	}

	public Date getEmployeeDob() {
		return this.employeeDob;
	}

	public void setEmployeeDob(Date employeeDob) {
		this.employeeDob = employeeDob;
	}

	public Date getEmployeeEditDate() {
		return this.employeeEditDate;
	}

	public void setEmployeeEditDate(Date employeeEditDate) {
		this.employeeEditDate = employeeEditDate;
	}

	public String getEmployeeEditLogin() {
		return this.employeeEditLogin;
	}

	public void setEmployeeEditLogin(String employeeEditLogin) {
		this.employeeEditLogin = employeeEditLogin;
	}

	public String getEmployeeEditStatus() {
		return this.employeeEditStatus;
	}

	public void setEmployeeEditStatus(String employeeEditStatus) {
		this.employeeEditStatus = employeeEditStatus;
	}

	public String getEmployeeEmail() {
		return this.employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public Date getEmployeeEntryDate() {
		return this.employeeEntryDate;
	}

	public void setEmployeeEntryDate(Date employeeEntryDate) {
		this.employeeEntryDate = employeeEntryDate;
	}

	public String getEmployeeGender() {
		return this.employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public Date getEmployeeJointDate() {
		return this.employeeJointDate;
	}

	public void setEmployeeJointDate(Date employeeJointDate) {
		this.employeeJointDate = employeeJointDate;
	}

	public String getEmployeeLoginStatus() {
		return this.employeeLoginStatus;
	}

	public void setEmployeeLoginStatus(String employeeLoginStatus) {
		this.employeeLoginStatus = employeeLoginStatus;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmpPhone() {
		return this.empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setEmployee(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setEmployee(null);

		return customer;
	}

	public Designation getDesignation() {
		return this.designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Qualification getQualification() {
		return this.qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public List<Payroll> getPayrolls() {
		return this.payrolls;
	}

	public void setPayrolls(List<Payroll> payrolls) {
		this.payrolls = payrolls;
	}

	public Payroll addPayroll(Payroll payroll) {
		getPayrolls().add(payroll);
		payroll.setEmployee(this);

		return payroll;
	}

	public Payroll removePayroll(Payroll payroll) {
		getPayrolls().remove(payroll);
		payroll.setEmployee(null);

		return payroll;
	}

	public List<ProjectEmployee> getProjectEmployees() {
		return this.projectEmployees;
	}

	public void setProjectEmployees(List<ProjectEmployee> projectEmployees) {
		this.projectEmployees = projectEmployees;
	}

	public ProjectEmployee addProjectEmployee(ProjectEmployee projectEmployee) {
		getProjectEmployees().add(projectEmployee);
		projectEmployee.setEmployee(this);

		return projectEmployee;
	}

	public ProjectEmployee removeProjectEmployee(ProjectEmployee projectEmployee) {
		getProjectEmployees().remove(projectEmployee);
		projectEmployee.setEmployee(null);

		return projectEmployee;
	}

	public List<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Purchase addPurchas(Purchase purchas) {
		getPurchases().add(purchas);
		purchas.setEmployee(this);

		return purchas;
	}

	public Purchase removePurchas(Purchase purchas) {
		getPurchases().remove(purchas);
		purchas.setEmployee(null);

		return purchas;
	}

	public List<Sale> getSales() {
		return this.sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Sale addSale(Sale sale) {
		getSales().add(sale);
		sale.setEmployee(this);

		return sale;
	}

	public Sale removeSale(Sale sale) {
		getSales().remove(sale);
		sale.setEmployee(null);

		return sale;
	}

	public List<StockoutManual> getStockoutManuals() {
		return this.stockoutManuals;
	}

	public void setStockoutManuals(List<StockoutManual> stockoutManuals) {
		this.stockoutManuals = stockoutManuals;
	}

	public StockoutManual addStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().add(stockoutManual);
		stockoutManual.setEmployee(this);

		return stockoutManual;
	}

	public StockoutManual removeStockoutManual(StockoutManual stockoutManual) {
		getStockoutManuals().remove(stockoutManual);
		stockoutManual.setEmployee(null);

		return stockoutManual;
	}

}