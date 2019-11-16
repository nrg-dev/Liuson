package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the designation database table.
 * 
 */
@Entity
@NamedQuery(name="Designation.findAll", query="SELECT d FROM Designation d")
public class Designation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int designationID;

	private String designation_Name;

	private String designation_Status;

	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="designation")
	private List<Employee> employees;

	public Designation() {
	}

	public int getDesignationID() {
		return this.designationID;
	}

	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	public String getDesignation_Name() {
		return this.designation_Name;
	}

	public void setDesignation_Name(String designation_Name) {
		this.designation_Name = designation_Name;
	}

	public String getDesignation_Status() {
		return this.designation_Status;
	}

	public void setDesignation_Status(String designation_Status) {
		this.designation_Status = designation_Status;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setDesignation(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setDesignation(null);

		return employee;
	}

}