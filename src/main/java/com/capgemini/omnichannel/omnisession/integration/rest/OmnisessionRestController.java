package com.capgemini.omnichannel.omnisession.integration.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.omnichannel.omnisession.integration.rest.dto.SessionRestDTO;
import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@RestController("session")
@RequestMapping("/session")
public class OmnisessionRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/{token}", method = RequestMethod.GET)
	@ResponseBody()
	public SessionRestDTO getSessionInfo(@PathVariable String token) {
		logger.debug("geting sessionDTO for token {}", token);

		SessionDTO sessionDTO = token != null ? getSessionService().getSessionDTO(token) : null;
		SessionRestDTO sessionRestDTO = null;

		if (sessionDTO != null) {
			sessionRestDTO = new SessionRestDTO(sessionDTO);
		}

		logger.debug("sessionDTO--> {}", sessionDTO);

		return sessionRestDTO;
	}

	@RequestMapping(value = "/{token}", method = RequestMethod.PUT)
	public SessionDTO updateSessionInfo(@PathVariable String token, @RequestBody SessionDTO value) {
		logger.debug("putting token/sessionDTO: {}/{} ", token, value);

		if (token != null) {
			getSessionService().updateSessionDTO(token, value);
		}

		return value;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
