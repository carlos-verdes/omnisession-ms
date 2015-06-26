package com.capgemini.omnichannel.omnisession.integration.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.capgemini.omnichannel.omnisession.integration.rest.config.RestTestConfiguration;
import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestTestConfiguration.class })
@WebAppConfiguration
public class OmnisessionRestControllerTests {

	private static final String REST_PATTERN = "/session/%s";

	private MockMvc mockMvc;

	private static final String USER_1 = "user1";
	private static final String USER_1_SESSION_1 = "userSession1";
	private static final String BAD_SESSION = "qqerqweCasdvadsfaind";

	@Autowired
	private SessionService sessionService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetById() throws Exception {

		// init mocks
		SessionDTO session = new SessionDTO();
		session.setUserId(USER_1);
		session.setToken(USER_1_SESSION_1);
		// new SessionDTO(USER_1, USER_1_SESSION_1)
		when(sessionService.getResourceById(USER_1_SESSION_1)).thenReturn(session);
		when(sessionService.getResourceById(BAD_SESSION)).thenReturn(null);

		String goodUri = String.format(REST_PATTERN, USER_1_SESSION_1);
		String badUri = String.format(REST_PATTERN, BAD_SESSION);

		mockMvc.perform(get(goodUri)).andExpect(status().isOk()).andExpect(jsonPath("$.data.userId", is(USER_1)))
				.andExpect(jsonPath("$.data.token", is(USER_1_SESSION_1)));

		mockMvc.perform(get(badUri)).andExpect(status().isNotFound());

	}

}
