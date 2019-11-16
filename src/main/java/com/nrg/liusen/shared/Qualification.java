package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the qualification database table.
 * 
 */
@Entity
@NamedQuery(name="Qualification.findAll", query="SELECT q FROM Qualification q")
public class Qualification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int qualificationID;

	private String qualification_Name;

	private String qualification_Status;

	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="qualification")
	private List<Employee> employees;

	public Qualification() {
	}

	public int getQualificationID() {
		return this.qualificationID;
	}

	public void setQualificationID(int qualificationID) {
		this.qualificationID = qualificationID;
	}

	public String getQualification_Name() {
		return this.qualification_Name;
	}

	public void setQualification_Name(String qualification_Name) {
		this.qualification_Name = qualification_Name;
	}

	public String getQualification_Status() {
		return this.qualification_Status;
	}

	public void setQualification_Status(String qualification_Status) {
		this.qualification_Status = qualification_Status;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setQualification(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setQualification(null);

		return employee;
	}

}