package com.example.frontend.security;

public class UserContext {

	private Long id;

	private String username;
	
	private String photo;

	private String token;

	

	public UserContext(Long id, String username, String photo, String token) {
		super();
		this.id = id;
		this.username = username;
		this.photo = photo;
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
