package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the payroll database table.
 * 
 */
@Entity
@NamedQuery(name="Payroll.findAll", query="SELECT p FROM Payroll p")
public class Payroll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int payroll_ID;

	@Column(name="approval_status")
	private String approvalStatus;

	@Column(name="commision_amount")
	private String commisionAmount;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	@Column(name="loan_advance")
	private String loanAdvance;

	@Column(name="ot_amoount")
	private String otAmoount;

	@Temporal(TemporalType.DATE)
	@Column(name="pay_date")
	private Date payDate;

	@Column(name="pay_login_status")
	private String payLoginStatus;

	@Column(name="pay_time")
	private Timestamp payTime;

	@Temporal(TemporalType.DATE)
	@Column(name="payroll_month")
	private Date payrollMonth;

	private String status;

	@Column(name="total_salary")
	private String totalSalary;

	@Column(name="working_days")
	private int workingDays;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;

	//bi-directional many-to-one association to PayrollApproval
	@OneToMany(mappedBy="payroll")
	private List<PayrollApproval> payrollApprovals;

	public Payroll() {
	}

	public int getPayroll_ID() {
		return this.payroll_ID;
	}

	public void setPayroll_ID(int payroll_ID) {
		this.payroll_ID = payroll_ID;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getCommisionAmount() {
		return this.commisionAmount;
	}

	public void setCommisionAmount(String commisionAmount) {
		this.commisionAmount = commisionAmount;
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

	public String getLoanAdvance() {
		return this.loanAdvance;
	}

	public void setLoanAdvance(String loanAdvance) {
		this.loanAdvance = loanAdvance;
	}

	public String getOtAmoount() {
		return this.otAmoount;
	}

	public void setOtAmoount(String otAmoount) {
		this.otAmoount = otAmoount;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayLoginStatus() {
		return this.payLoginStatus;
	}

	public void setPayLoginStatus(String payLoginStatus) {
		this.payLoginStatus = payLoginStatus;
	}

	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	

	public Date getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(Date payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalSalary() {
		return this.totalSalary;
	}

	public void setTotalSalary(String totalSalary) {
		this.totalSalary = totalSalary;
	}

	public int getWorkingDays() {
		return this.workingDays;
	}

	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<PayrollApproval> getPayrollApprovals() {
		return this.payrollApprovals;
	}

	public void setPayrollApprovals(List<PayrollApproval> payrollApprovals) {
		this.payrollApprovals = payrollApprovals;
	}

	public PayrollApproval addPayrollApproval(PayrollApproval payrollApproval) {
		getPayrollApprovals().add(payrollApproval);
		payrollApproval.setPayroll(this);

		return payrollApproval;
	}

	public PayrollApproval removePayrollApproval(PayrollApproval payrollApproval) {
		getPayrollApprovals().remove(payrollApproval);
		payrollApproval.setPayroll(null);

		return payrollApproval;
	}

}