package com.capgemini.omnichannel.omnisession.model.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

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

	private final String userId;

	private ConcurrentHashMap<String, SessionDTO> sessionMap = new ConcurrentHashMap<String, SessionDTO>();

	private ConcurrentHashMap<String, Object> mergedPayload = new ConcurrentHashMap<String, Object>();

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
	 */
	public void addSession(SessionDTO session) throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException, IntrospectionException {
		if (session != null) {
			String token = session.getToken();
			this.sessionMap.put(token, session);

			mergePayloads(session.getUserId());
		}

	}

	private void mergePayloads(String sessionId) throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException, IntrospectionException {

		ConcurrentHashMap<String, Object> mergedPayload = this.mergedPayload;
		Object sourcePayload = this.sessionMap.get(sessionId);

		if (sourcePayload != null) {

			BeanInfo beanInfo = Introspector.getBeanInfo(sourcePayload.getClass());

			// Iterate over all the attributes
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
				String propertyName = descriptor.getDisplayName();

				Object propertyValue = descriptor.getReadMethod().invoke(sourcePayload);

//				ConcurrentHashMap<String,Object> payloadValue= this.mergedPayload.getOrDefault(propertyName, new ConcurrentHashMap<String, Object>());
				
				Object currentValue = this.mergedPayload.get(propertyName);
				mergeObjects(propertyValue, currentValue);

			}
		}

	}

	private void mergeObjects(Object source, Object target) {

	}

	/**
	 * Retrieves the user id
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Get the global session payload (a mix of all the sessions).
	 * 
	 * @return
	 */
	public Object getMergedPayload() {
		return mergedPayload;
	}

}
