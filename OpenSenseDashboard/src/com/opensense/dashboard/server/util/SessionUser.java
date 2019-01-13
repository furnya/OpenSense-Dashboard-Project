package com.opensense.dashboard.server.util;

public class SessionUser {

	private static SessionUser instance;

	private boolean isGuest = false;
	private Integer userId;
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
		return this.isGuest;
	}

	public void removeUser() {
		this.isGuest = true;
		this.userId = null;
		this.username = null;
		this.token = null;
	}

	public void createUser(int userId, String username, String token) {
		this.isGuest = false;
		this.userId = userId;
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public String getToken() {
		return this.token;
	}

	public Integer getUserId() {
		return this.userId;
	}


}
