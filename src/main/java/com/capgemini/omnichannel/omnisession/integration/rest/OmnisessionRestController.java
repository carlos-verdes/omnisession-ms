package com.capgemini.omnichannel.omnisession.integration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.EntityPersistenceService;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@RestController("session")
@RequestMapping("/session")
public class OmnisessionRestController extends BaseEntityRestController<SessionDTO>{

	@Autowired
	private SessionService sessionService;
	
	@Override
	EntityPersistenceService<SessionDTO> getEntityPersistenceService() {
		return this.sessionService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}


}
