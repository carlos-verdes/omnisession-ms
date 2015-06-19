package com.capgemini.omnichannel.omnisession.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDTO implements Serializable {
	private static final long serialVersionUID = 6462178045991916844L;

	private String userId;
	private String token;

	private ConcurrentHashMap<String, Object> payload = new ConcurrentHashMap<String, Object>();

	public SessionDTO() {
		super();
	}

	public SessionDTO(String userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}

	
	
	@Override
	public String toString() {
		return "SessionDTO [userId=" + userId + ", token=" + token + ", payload=" + payload + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 1283;
		int result = 1;
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionDTO other = (SessionDTO) obj;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public Map<String, Object> getPayload() {
		return new HashMap<String, Object>(this.payload);
	}

	public void putData(String key, Object value) {
		this.payload.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(String key, Class<? extends T> clazz) {
		T result = (T) this.payload.get(key);

		return result;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPayload(ConcurrentHashMap<String, Object> payload) {
		this.payload = payload;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
