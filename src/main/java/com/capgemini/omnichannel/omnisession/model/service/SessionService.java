package com.capgemini.omnichannel.omnisession.model.service;

import java.util.List;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;

public interface SessionService {

	
	/**
	 * Create a new session. It retrieves the userId from the session and save it so all the sessions from this user could be retrieved {@link SessionService#getRelatedSessions(String) getReladSessions}
	 * @param token
	 * @param sessionDTO
	 */
	public void updateSessionDTO(String token, SessionDTO sessionDTO);

	/**
	 * Search in the cache a session associated with a token.
	 * @param token
	 * @return
	 */
	public SessionDTO getSessionDTO(String token);

	/**
	 * Retrieves the sessions associated with the same user.
	 * @param token user session token
	 * @return empty if no related sessions
	 */
	public List<SessionDTO> getRelatedSessions(String token);


	/**
	 * Retrieves some data from one session.
	 * @param token
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz);

	/**
	 * Put data into one session.
	 * @param token
	 * @param key
	 * @param data
	 */
	public <T> void updateSessionData(String token, String key, T data);

}
