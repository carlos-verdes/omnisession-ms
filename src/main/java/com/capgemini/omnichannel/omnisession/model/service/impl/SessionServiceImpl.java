package com.capgemini.omnichannel.omnisession.model.service.impl;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.CacheService;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private CacheService cacheService;

	@Value("${omnisession.cacheName}")
	private String cacheName;

	@Override
	public SessionDTO getSessionDTO(String token) {
		return cacheService.get(token, cacheName);
	}

	@Override
	public void updateSessionDTO(String token, SessionDTO sessionDTO) {
		cacheService.put(token, cacheName, sessionDTO);
	}

	/**
	 * If session exist then do something
	 * 
	 * @param token
	 *            session token
	 * @param doWithSession
	 *            function that receives the session
	 */
	public void doWithSession(String token, Consumer<SessionDTO> doWithSession) {

		if (cacheService.containsKey(token, cacheName)) {
			SessionDTO sessionDTO = getSessionDTO(token);

			doWithSession.accept(sessionDTO);

		}

	}

	@Override
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz) {
		T result = null;

		if (cacheService.containsKey(token, cacheName)) {
			SessionDTO sessionDTO = getSessionDTO(token);

			result = sessionDTO.getData(key, clazz);
		}

		return result;
	}

	@Override
	public <T> void updateSessionData(String token, String key, T data) {

		// put data into session
		doWithSession(token, session -> session.putData(key, data));

	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
