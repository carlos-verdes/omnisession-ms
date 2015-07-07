package com.capgemini.omnichannel.omnisession.model.service;

import java.security.Principal;
import java.util.List;

import com.capgemini.omnichannel.common.model.service.ResourcePersistenceService;
import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;

public interface SessionService extends ResourcePersistenceService<SessionDTO> {

	/**
	 * Retrieves the sessions associated with the same user.
	 * 
	 * @param token
	 *            user session token
	 * @return empty if no related sessions
	 */
	public List<SessionDTO> getRelatedSessions(String token, Principal principal);

	/**
	 * Retrieves some data from one session.
	 * 
	 * @param token
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz, Principal principal);

	/**
	 * Put data into one session.
	 * 
	 * @param token
	 * @param key
	 * @param data
	 */
	public <T> void updateSessionData(String token, String key, T data, Principal principal);

}
