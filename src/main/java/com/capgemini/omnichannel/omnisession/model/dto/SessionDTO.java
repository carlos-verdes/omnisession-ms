package com.capgemini.omnichannel.omnisession.model.dto;

import java.io.Serializable;

public class SessionDTO implements Serializable {
	private static final long serialVersionUID = 6462178045991916844L;

	private String userId;

	private String token;

	private Object payload;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
