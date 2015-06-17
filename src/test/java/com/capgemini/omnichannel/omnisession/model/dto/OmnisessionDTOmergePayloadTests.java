/**
 * 
 */
package com.capgemini.omnichannel.omnisession.model.dto;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cverdesm
 *
 */
public class OmnisessionDTOmergePayloadTests {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String USER_1 = "user1";
	private final static String USER_2 = "user2";
	private final static String TOKEN_WEB_USER_1 = "web_session_user1";
	private final static String TOKEN_ANDROID_USER_1 = "android_session_user1";
	private final static String PERSON_KEY = "person";

	private OmnisessionDTO omniSession;

	@Before
	public void initSession() {

		omniSession = new OmnisessionDTO(USER_1);
	}

	/**
	 * Test method for
	 * {@link com.capgemini.omnichannel.omnisession.model.dto.OmnisessionDTO#addSession(com.capgemini.omnichannel.omnisession.model.dto.SessionMetadataDTO)}
	 * .
	 * 
	 * @throws UserIdSessionDoesntMatchException
	 * @throws IntrospectionException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testAddSession() throws IllegalAccessException, InstantiationException, InvocationTargetException,
			NoSuchMethodException, IntrospectionException, UserIdSessionDoesntMatchException {

		SessionMetadataDTO session1 = new SessionMetadataDTO(USER_1, TOKEN_WEB_USER_1);
		omniSession.addSession(session1);

	}

	/**
	 * Test session userId can't be null in method for
	 * {@link com.capgemini.omnichannel.omnisession.model.dto.OmnisessionDTO#addSession(com.capgemini.omnichannel.omnisession.model.dto.SessionMetadataDTO)}
	 * .
	 */
	@Test
	public void testAddSessionUserCantBeNull() {
		new OmnisessionDTO(null);

	}

	/**
	 * Test SessionMetadaDTO.userId has to be the same as OmnisessionDTO.userId in method for
	 * {@link com.capgemini.omnichannel.omnisession.model.dto.OmnisessionDTO#addSession(com.capgemini.omnichannel.omnisession.model.dto.SessionMetadataDTO)}
	 * .
	 * 
	 * @throws UserIdSessionDoesntMatchException
	 * @throws IntrospectionException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test(expected = UserIdSessionDoesntMatchException.class)
	public void testAddSessionUserIdMustMatch() throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException, IntrospectionException, UserIdSessionDoesntMatchException {
		this.omniSession.addSession(new SessionMetadataDTO(USER_2, null));

	}

	/**
	 * Test method for
	 * {@link com.capgemini.omnichannel.omnisession.model.dto.OmnisessionDTO#storeDataInSession(java.lang.String, java.lang.String, java.lang.Object)}
	 * and {@link com.capgemini.omnichannel.omnisession.model.dto.OmnisessionDTO#getMergedPayload()} .
	 * 
	 * @throws UserIdSessionDoesntMatchException
	 * @throws IntrospectionException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testStoreDataInSessionAndGet() throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException, IntrospectionException, UserIdSessionDoesntMatchException {
		SessionMetadataDTO session1 = new SessionMetadataDTO(USER_1, TOKEN_WEB_USER_1);
		SessionMetadataDTO session2 = new SessionMetadataDTO(USER_1, TOKEN_ANDROID_USER_1);

		// create two session over same omnisession
		omniSession.addSession(session1);
		omniSession.addSession(session2);

		// insert two different classes in each session with same key

		// insert person in web session
		PersonType1MockClass personInfo1 = new PersonType1MockClass("John", "Jackson", 50, "friend1", "friend2");
		logger.debug(String.format("Inserting person in session %s: %s", TOKEN_WEB_USER_1, personInfo1));

		//store info in first session
		omniSession.storeDataInSession(TOKEN_WEB_USER_1, PERSON_KEY, personInfo1);

		// check if person info is stored properly in first session
		PersonType1MockClass personInfo1FromSession = omniSession.getDataFromSession(TOKEN_WEB_USER_1, PERSON_KEY,
				PersonType1MockClass.class);
		logger.debug(String.format("Retrieveing person from session %s: %s", TOKEN_WEB_USER_1, personInfo1FromSession));
		assertEquals(personInfo1, personInfo1FromSession);

		// insert person in android session
		PersonType2MockClass personInfo2 = new PersonType2MockClass("John", "Travolta", 99, "no parent", "Location",
				"NY");
		logger.debug(String.format("Inserting person in session %s: %s", TOKEN_ANDROID_USER_1, personInfo2));

		//store info in second session
		omniSession.storeDataInSession(TOKEN_ANDROID_USER_1, PERSON_KEY, personInfo2);

		
		// check if person info is stored properly in andorid session
		PersonType2MockClass personInfo2FromSession = omniSession.getDataFromSession(TOKEN_ANDROID_USER_1, PERSON_KEY,
				PersonType2MockClass.class);
		logger.debug(String.format("Retrieveing person from session %s: %s", TOKEN_ANDROID_USER_1,
				personInfo2FromSession));
		assertEquals(personInfo2, personInfo2FromSession);

	}

	


}
