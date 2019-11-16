package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int user_ID;

	private String login_UserName;

	private String online_Status;

	private String user_Name;

	private String user_Password;

	private String user_Status;

	//bi-directional many-to-one association to UserRoll
	@OneToMany(mappedBy="user")
	private List<UserRoll> userRolls;

	public User() {
	}

	public int getUser_ID() {
		return this.user_ID;
	}

	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}

	public String getLogin_UserName() {
		return this.login_UserName;
	}

	public void setLogin_UserName(String login_UserName) {
		this.login_UserName = login_UserName;
	}

	public String getOnline_Status() {
		return this.online_Status;
	}

	public void setOnline_Status(String online_Status) {
		this.online_Status = online_Status;
	}

	public String getUser_Name() {
		return this.user_Name;
	}

	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}

	public String getUser_Password() {
		return this.user_Password;
	}

	public void setUser_Password(String user_Password) {
		this.user_Password = user_Password;
	}

	public String getUser_Status() {
		return this.user_Status;
	}

	public void setUser_Status(String user_Status) {
		this.user_Status = user_Status;
	}

	public List<UserRoll> getUserRolls() {
		return this.userRolls;
	}

	public void setUserRolls(List<UserRoll> userRolls) {
		this.userRolls = userRolls;
	}

	public UserRoll addUserRoll(UserRoll userRoll) {
		getUserRolls().add(userRoll);
		userRoll.setUser(this);

		return userRoll;
	}

	public UserRoll removeUserRoll(UserRoll userRoll) {
		getUserRolls().remove(userRoll);
		userRoll.setUser(null);

		return userRoll;
	}

}