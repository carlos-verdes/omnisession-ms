package com.capgemini.omnichannel.omnisession.model.service;

public interface CacheService {

	public abstract <T> T get(String key);

	public abstract <T> T get(String key, String cacheName);

	public abstract void put(String key, Object value);

	public abstract void put(String key, String cacheName, Object value);

}