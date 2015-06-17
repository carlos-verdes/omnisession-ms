package com.capgemini.omnichannel.omnisession.model.service;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;

public interface SessionService {

	public SessionDTO getSessionDTO(String token);
	public void updateSessionDTO(String token, SessionDTO sessionDTO);
	
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz);
	public <T> void updateSessionData(String token, String key, T data);
	
}
