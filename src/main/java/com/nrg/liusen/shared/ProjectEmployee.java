package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the project_employee database table.
 * 
 */
@Entity
@Table(name="project_employee")
@NamedQuery(name="ProjectEmployee.findAll", query="SELECT p FROM ProjectEmployee p")
public class ProjectEmployee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_project_ID;

	private String status;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_ID")
	private Employee employee;

	//bi-directional many-to-one association to Projet
	@ManyToOne
	@JoinColumn(name="project_ID")
	private Projet projet;

	public ProjectEmployee() {
	}

	public int getHas_project_ID() {
		return this.has_project_ID;
	}

	public void setHas_project_ID(int has_project_ID) {
		this.has_project_ID = has_project_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Projet getProjet() {
		return this.projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

}