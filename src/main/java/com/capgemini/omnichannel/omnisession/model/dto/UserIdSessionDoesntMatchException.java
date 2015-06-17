package com.capgemini.omnichannel.omnisession.model.dto;

public class UserIdSessionDoesntMatchException extends Exception {
	private static final long serialVersionUID = 4436086991891692338L;

	private final static String INTERNAL_MESSAGE = "The userId %s doesn't match with the session userID, session: %s";

	public UserIdSessionDoesntMatchException(String sessionId, OmnisessionDTO omnisession) {
		super(String.format(INTERNAL_MESSAGE, sessionId, omnisession));

	}

}
