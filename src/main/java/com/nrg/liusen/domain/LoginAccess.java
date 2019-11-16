package com.nrg.liusen.domain;

/**
 * This Java Class will communicate with Domain Object
 * Robert Arjun
 * @date 01-11-2015
 * @copyright NRG
 *       
 */
public class LoginAccess {

	
	String username;
	String userpassword;
	String userStatus;
	String user_rolles;
	String login_user;
	
	
	/**
	 * @return the login_user
	 */
	public String getLogin_user() {
		return login_user;
	}
	/**
	 * @param login_user the login_user to set
	 */
	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}
	/**
	 * @return the user_rolles
	 */
	public String getUser_rolles() {
		return user_rolles;
	}
	/**
	 * @param user_rolles the user_rolles to set
	 */
	public void setUser_rolles(String user_rolles) {
		this.user_rolles = user_rolles;
	}
	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}
	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	
	
	
	
	
	
}
