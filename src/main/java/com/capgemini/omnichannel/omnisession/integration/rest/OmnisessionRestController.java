package com.capgemini.omnichannel.omnisession.integration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.omnichannel.common.integration.rest.BaseResourceRestController;
import com.capgemini.omnichannel.common.model.service.ResourcePersistenceService;
import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@RestController("sessions")
@RequestMapping("/sessions")
public class OmnisessionRestController extends BaseResourceRestController<SessionDTO, String> {

	@Autowired
	private SessionService sessionService;

	@Override
	public ResourcePersistenceService<SessionDTO,String> getResourcePersistenceService() {
		return this.sessionService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
