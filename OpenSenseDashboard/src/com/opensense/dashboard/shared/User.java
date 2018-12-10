package com.opensense.dashboard.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable{

	//	private JSONObject rawJSON;
	private int id;
	private String username;
	private String password;
	private String email;
	private String emailVerified;
	private Date created;

	public User() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}


	/**
	 * @return the emailVerified
	 */
	public String getEmailVerified() {
		return this.emailVerified;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return this.created;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}


	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

}
