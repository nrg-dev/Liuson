package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_roll database table.
 * 
 */
@Entity
@Table(name="user_roll")
@NamedQuery(name="UserRoll.findAll", query="SELECT u FROM UserRoll u")
public class UserRoll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int user_rollID;

	private String user_roll_Status;

	//bi-directional many-to-one association to Roll
	@ManyToOne
	@JoinColumn(name="roll_ID")
	private Roll roll;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_ID")
	private User user;

	public UserRoll() {
	}

	public int getUser_rollID() {
		return this.user_rollID;
	}

	public void setUser_rollID(int user_rollID) {
		this.user_rollID = user_rollID;
	}

	public String getUser_roll_Status() {
		return this.user_roll_Status;
	}

	public void setUser_roll_Status(String user_roll_Status) {
		this.user_roll_Status = user_roll_Status;
	}

	public Roll getRoll() {
		return this.roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}