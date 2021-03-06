package com.fi9e.rest.models;

/**
 * Holds User Credentials
 * 
 * @author Christopher
 *
 */
public class UserCredentials {
	private String userName;
	private String password;
	
	public UserCredentials(String username, String password) {
		this.setPassword(password);
		this.setUserName(username);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
