package com.capgemini.omnichannel.omnisession.integration.rest.config;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@Configuration()
@ComponentScan({ "com.capgemini.omnichannel.omnisession.integration.rest",
		"com.capgemini.omnichannel.common.integration.rest" })
@EnableWebMvc()
public class RestTestConfiguration {

	@Bean
	public SessionService sessionService() {
		SessionService mock = Mockito.mock(SessionService.class);
		
		return mock;
	}

}
