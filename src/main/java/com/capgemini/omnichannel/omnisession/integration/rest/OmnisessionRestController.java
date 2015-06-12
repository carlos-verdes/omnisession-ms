package com.capgemini.omnichannel.omnisession.integration.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.omnichannel.omnisession.model.service.CacheService;

@RestController("session")
@RequestMapping("/session")
public class OmnisessionRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public String getSessionInfo(@PathVariable String key) {
		logger.debug("geting value {0}", key);

		Object value = key != null ? cacheService.get(key) : "";
		logger.debug("value is {}", value);

		return String.format("hello world, value is %s", value);
	}

	@RequestMapping(value = "/{key}", method = RequestMethod.PUT)
	public String updateSessionInfo(@PathVariable String key, @RequestBody String value) {
		logger.debug("putting {}/{} key/value", key, value);
		if (key != null) {
			cacheService.put(key, value);
		}

		return null;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

}
