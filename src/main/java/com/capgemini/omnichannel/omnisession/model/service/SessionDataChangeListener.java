package com.capgemini.omnichannel.omnisession.model.service;

/**
 * Listener which is notified when there is a change in a session data.
 * 
 * @author cverdesm
 *
 */
public interface SessionDataChangeListener {

	/**
	 * Invoked when data has changed.
	 * 
	 * @param userId
	 *            user id
	 * @param token
	 *            session token id
	 * @param oldPayload
	 *            old data for this session, null if empty
	 * @param newPayload
	 *            new data for this session
	 */
	void onSessionDataChange(String userId, String token, Object oldPayload, Object newPayload);
}
