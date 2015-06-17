package com.capgemini.omnichannel.omnisession.model.dto;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Provides a global session for a user.
 * <p>
 * The method #
 * 
 * @author cverdesm
 *
 */
public class OmnisessionDTO implements Serializable {
	private static final long serialVersionUID = 1843131912441967616L;

	@NotNull
	private final String userId;

	private ConcurrentHashMap<String, SessionMetadataDTO> sessionMap = new ConcurrentHashMap<String, SessionMetadataDTO>();

	public OmnisessionDTO(String userId) {
		super();
		this.userId = userId;
	}

	/**
	 * Add session to global session and merge payloads to the omni session payload is a mix of all the sessions
	 * payload.
	 * 
	 * @param session
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws UserIdSessionDoesntMatchException
	 */
	public void addSession(SessionMetadataDTO session) throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException, IntrospectionException, UserIdSessionDoesntMatchException {
		if (session != null) {
			if (!session.getUserId().equals(this.getUserId())) {
				throw new UserIdSessionDoesntMatchException(session.getUserId(), this);
			}
			String token = session.getToken();
			this.sessionMap.put(token, session);
		}

	}

	public void storeDataInSession(SessionMetadataDTO session, String key, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IntrospectionException {

		if (session != null) {
			storeDataInSession(session.getToken(), key, value);

		}
	}

	public void storeDataInSession(String token, String key, Object value) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IntrospectionException {

		if (this.sessionMap.containsKey(token)) {
			SessionMetadataDTO sessionDTO = this.sessionMap.get(token);
			sessionDTO.putData(key, value);
		}
	}

	public <T> T getDataFromSession(String token, String key, Class<? extends T> clazz) {

		T result = null;
		if (this.sessionMap.containsKey(token)) {

			SessionMetadataDTO sessionDTO = this.sessionMap.get(token);
			result = sessionDTO.getData(key, clazz);

		}
		return result;
	}

	/**
	 * Retrieves the user id
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

}
