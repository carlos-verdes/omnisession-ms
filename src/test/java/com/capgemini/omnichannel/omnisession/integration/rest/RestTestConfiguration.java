package com.capgemini.omnichannel.omnisession.integration.rest;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@Configuration()
@ComponentScan({ "com.capgemini.omnichannel.omnisession.integration.rest",
		"com.capgemini.omnichannel.common.integration.rest" })
@EnableWebMvc()
public class RestTestConfiguration {

	@Bean
	public SessionService sessionService() {
		return Mockito.mock(SessionService.class);
	}

}
