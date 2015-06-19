package com.capgemini.omnichannel.omnisession.model.service;

public interface CacheService {

	public <T> T get(String key);

	public <T> T get(String key, String cacheName);

	public <T> T getOrDefault(String key, Object defaultValue);

	public <T> T getOrDefault(String key, String cacheName, Object defaultValue);

	public void put(String key, Object value);

	public void put(String key, String cacheName, Object value);

	public void putIfAbsent(String key, Object value);

	public void putIfAbsent(String key, String cacheName, Object value);

	
	public boolean containsKey(String key, String cacheName);

	public boolean containsKey(String key);

}