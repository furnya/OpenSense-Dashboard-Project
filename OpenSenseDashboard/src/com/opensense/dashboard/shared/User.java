package com.opensense.dashboard.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable{

	private JSONObject rawJSON;
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
		return id;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return this.getRawJSON().toString();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return the emailVerified
	 */
	public String getEmailVerified() {
		return emailVerified;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @return the rawJSON
	 */
	public JSONObject getRawJSON() {
		return rawJSON;
	}


	/**
	 * @param rawJSON the rawJSON to set
	 */
	public void setRawJSON(JSONObject rawJSON) {
		this.rawJSON = rawJSON;
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
		return password;
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
