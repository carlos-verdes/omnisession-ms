package com.capgemini.omnichannel.omnisession.model.service;

public interface CacheService {

	public <T> T get(String key);

	public <T> T get(String key, String cacheName);

	public <T> T getOrDefault(String key, Object defaultValue);

	public <T> T getOrDefault(String key, String cacheName, Object defaultValue);

	public <T> T put(String key, T value);

	public <T> T put(String key, String cacheName, T value);

	public <T> T putIfAbsent(String key, T value);

	public <T> T putIfAbsent(String key, String cacheName, T value);

	
	public boolean containsKey(String key, String cacheName);

	public boolean containsKey(String key);

}