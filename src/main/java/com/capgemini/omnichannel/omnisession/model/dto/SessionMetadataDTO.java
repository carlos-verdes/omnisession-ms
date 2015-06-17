package com.capgemini.omnichannel.omnisession.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMetadataDTO implements Serializable {
	private static final long serialVersionUID = 6462178045991916844L;

	private final  String userId;
	private final String token;
	
	private ConcurrentHashMap<String, Object> payload= new ConcurrentHashMap<String, Object>();

	public SessionMetadataDTO(String userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
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
	
	public void putData(String key, Object value){
		this.payload.put(key, value);
	}
	
	public<T> T getData(String key, Class<? extends T> clazz){
		T result= (T) this.payload.get(key);
		
		return result;
	}



}
