package com.opensense.dashboard.server.util;

public class SessionUser {
	
	private static SessionUser instance;
	
	private boolean isGuest = false;
	private String username;
	private String token;
	//implement expire date about 1h that the session destroys itself and automatically build new one?
	
	public static SessionUser getInstance() {
		if(instance == null) {
			instance = new SessionUser();
		}
		return instance;
	}
	
	private SessionUser() {
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
