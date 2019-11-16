package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the roll database table.
 * 
 */
@Entity
@NamedQuery(name="Roll.findAll", query="SELECT r FROM Roll r")
public class Roll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int roll_ID;

	private String roll_Name;

	private String roll_Status;

	//bi-directional many-to-one association to UserRoll
	@OneToMany(mappedBy="roll")
	private List<UserRoll> userRolls;

	public Roll() {
	}

	public int getRoll_ID() {
		return this.roll_ID;
	}

	public void setRoll_ID(int roll_ID) {
		this.roll_ID = roll_ID;
	}

	public String getRoll_Name() {
		return this.roll_Name;
	}

	public void setRoll_Name(String roll_Name) {
		this.roll_Name = roll_Name;
	}

	public String getRoll_Status() {
		return this.roll_Status;
	}

	public void setRoll_Status(String roll_Status) {
		this.roll_Status = roll_Status;
	}

	public List<UserRoll> getUserRolls() {
		return this.userRolls;
	}

	public void setUserRolls(List<UserRoll> userRolls) {
		this.userRolls = userRolls;
	}

	public UserRoll addUserRoll(UserRoll userRoll) {
		getUserRolls().add(userRoll);
		userRoll.setRoll(this);

		return userRoll;
	}

	public UserRoll removeUserRoll(UserRoll userRoll) {
		getUserRolls().remove(userRoll);
		userRoll.setRoll(null);

		return userRoll;
	}

}