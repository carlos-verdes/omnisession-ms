package com.capgemini.omnichannel.omnisession.integration.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	private static final String REST_PATTERN = "/sessions/%s";

	private MockMvc mockMvc;

	private static final String USER_1 = "user1";
	private static final String USER_1_SESSION_1_ID = "userSession1";
	private static final String BAD_SESSION = "qqerqweCasdvadsfaind";

	@Autowired
	private SessionService sessionService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private Principal user1;
	private SessionDTO sessionUser1Web1;

	@Before
	public void setUp() {
		// init mocks
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		sessionUser1Web1 = new SessionDTO();
		sessionUser1Web1.setUserId(USER_1);
		sessionUser1Web1.setToken(USER_1_SESSION_1_ID);

		user1 = new Principal() {

			@Override
			public String getName() {
				return USER_1;
			}
		};

		SessionDTO userSession = new SessionDTO(USER_1, USER_1_SESSION_1_ID);
		List<SessionDTO> user1Sessions = new ArrayList<SessionDTO>(Arrays.asList(userSession));

		// mock general get resources
		when(sessionService.getResources(user1)).thenReturn(user1Sessions);
		when(sessionService.getResources(null)).thenReturn(Collections.emptyList());

		// mock resource by id
		when(sessionService.getResourceById(USER_1_SESSION_1_ID, user1)).thenReturn(sessionUser1Web1);
		when(sessionService.getResourceById(BAD_SESSION, user1)).thenReturn(null);

	}

	@Test
	public void testGetUserSessions() throws Exception {
		String uri = String.format(REST_PATTERN, "");

		// with principal
		mockMvc.perform(get(uri).principal(user1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].data.userId", is(USER_1)))
				.andExpect(jsonPath("$.data[0].data.token", is(USER_1_SESSION_1_ID)));

		// without principal
		mockMvc.perform(get(uri)).andExpect(status().isNotFound());

	}

	@Test
	public void testGetById() throws Exception {

		String goodUri = String.format(REST_PATTERN, USER_1_SESSION_1_ID);
		String badUri = String.format(REST_PATTERN, BAD_SESSION);

		// with principal and good uri
		mockMvc.perform(get(goodUri).principal(user1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.userId", is(USER_1)))
				.andExpect(jsonPath("$.data.token", is(USER_1_SESSION_1_ID)));

		// with principal and bad uri
		mockMvc.perform(get(badUri).principal(user1)).andExpect(status().isNotFound());

		// without principal and good uri
		mockMvc.perform(get(goodUri)).andExpect(status().isNotFound());

	}

}
