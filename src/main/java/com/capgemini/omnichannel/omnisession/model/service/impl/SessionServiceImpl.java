package com.capgemini.omnichannel.omnisession.model.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.capgemini.omnichannel.omnisession.model.dto.SessionDTO;
import com.capgemini.omnichannel.omnisession.model.service.CacheService;
import com.capgemini.omnichannel.omnisession.model.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
	private static final HashSet<String> DEFAULT_USER_SESSION_SET = new HashSet<String>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${omnisession.tokenSessionCache}")
	private String tokenSessionCache;

	@Value("${omnisession.userTokensCache}")
	private String userTokensCache;

	@Autowired
	private CacheService cacheService;


	@Override
	public String getResourceId(SessionDTO resource) {
		return userNameLambda(resource).get();
	}

	@Override
	public List<SessionDTO> getResources(Principal principal) {

		List<SessionDTO> result = getRelatedSessionsLambda(userNameLambda(principal), principal);

		return result;
	}

	@Override
	public SessionDTO getResourceById(String id, Principal principal) {
		SessionDTO sessionDTO = cacheService.get(id, tokenSessionCache);

		if (sessionDTO != null) {
			touchOmnisession(sessionDTO);
		}

		return sessionDTO;
	}

	@Override
	public <S extends SessionDTO> S updateResource(String id, S value, Principal principal) {
		logger.debug(String.format("updating Resource, id:value -->  %s: %s", id, value));

		// update omnisession
		updateOmnisession(value);

		// update current session
		cacheService.put(id, tokenSessionCache, value);

		return value;
	}

	@Override
	public <S extends SessionDTO> S insertResource(String id, S value, Principal principal) {
		logger.debug(String.format("inserting Resource, id:value -->  %s: %s", id, value));

		// update omnisession
		updateOmnisession(value);

		// update current session
		cacheService.putIfAbsent(id, tokenSessionCache, value);

		return value;
	}

	/**
	 * If session exist then do something
	 * 
	 * @param token
	 *            session token
	 * @param doWithSession
	 *            function that receives the session
	 */
	public void doWithSession(String token, Consumer<SessionDTO> doWithSession, Principal principal) {

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			SessionDTO sessionDTO = this.getResourceById(token, principal);

			// touch omnisession to update idle timeouts
			touchOmnisession(sessionDTO);

			doWithSession.accept(sessionDTO);

		}

	}

	public List<SessionDTO> getRelatedSessionsLambda(Supplier<String> userNameSupplier, Principal principal) {
		// TODO review if a user could get omnisession from other user

		final List<SessionDTO> relatedSessions = new ArrayList<SessionDTO>();

		Set<String> userSessionIdSet = getUserSessions(userNameSupplier);

		// if related sessions exist
		if (userSessionIdSet != null && !userSessionIdSet.isEmpty()) {
			Iterator<String> iter = userSessionIdSet.iterator();
			while (iter.hasNext()) {
				String relatedToken = iter.next();

				SessionDTO relatedSession = this.getResourceById(relatedToken, principal);

				// if exist in cache
				if (relatedSession != null) {
					// add to results if different
					relatedSessions.add(relatedSession);
				} else {
					// remove from omnisession
					iter.remove();
				}

			}
			// update omnisession (some session could be expired)
			updateOmnisessionCache(userNameSupplier, userSessionIdSet);

		}

		return relatedSessions;
	}

	@Override
	public List<SessionDTO> getRelatedSessions(String token, Principal principal) {

		List<SessionDTO> relatedSessions = null;

		// if session exist
		SessionDTO sessionDTO = this.getResourceById(token, principal);
		Supplier<String> userNameLambda = userNameLambda(sessionDTO);
		relatedSessions = getRelatedSessionsLambda(userNameLambda, principal);

		return relatedSessions;
	}

	@Override
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz, Principal principal) {
		T result = null;

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			SessionDTO sessionDTO = this.getResourceById(token, principal);

			result = sessionDTO.retrieveDataFromPayload(key, clazz);
		}

		return result;
	}

	@Override
	public <T> void updateSessionData(String token, String key, T data, Principal principal) {

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			// get the session
			SessionDTO sessionDTO = this.getResourceById(token, principal);

			// update session
			sessionDTO.putDataIntoPayload(key, data);

		}

	}

	// private Set<String> getUserSessions(final Principal principal) {
	// Set<String> result = principal != null ? getUserSessions(() -> principal.getName()())
	// : DEFAULT_USER_SESSION_SET;
	//
	// return result;
	//
	// }

	private Set<String> getUserSessions(Supplier<String> userSupplier) {

		String userId = userSupplier != null ? userSupplier.get() : null;

		Set<String> result = userId != null ? this.cacheService.getOrDefault(userId, this.userTokensCache,
				DEFAULT_USER_SESSION_SET) : DEFAULT_USER_SESSION_SET;
		return result;

	}

	private static Supplier<String> userNameLambda(final SessionDTO sessionDTO) {
		return () -> sessionDTO != null ? sessionDTO.getUserId() : null;
	}

	private static Supplier<String> userNameLambda(final Principal principal) {
		return () -> principal != null ? principal.getName() : null;
	}

	private void touchOmnisession(final SessionDTO sessionDTO) {
		// just check the omnisession to update timeouts
		if (sessionDTO != null) {
			getUserSessions(userNameLambda(sessionDTO));

		}

	}

	private void updateOmnisession(SessionDTO sessionDTO) {

		if (sessionDTO != null) {

			Supplier<String> userNameLambda = userNameLambda(sessionDTO);

			// get user sessions (or default)
			Set<String> userSessionIdsSet = getUserSessions(userNameLambda);

			// add current session
			userSessionIdsSet.add(sessionDTO.getToken());

			// update cache
			updateOmnisessionCache(userNameLambda, userSessionIdsSet);

		}

	}

	private void updateOmnisessionCache(Supplier<String> userNameSupplier, Set<String> userSessionIdsSet) {
		String userId = userNameSupplier.get();

		if (userId != null) {
			// put in omnisession in cache
			this.cacheService.put(userId, this.userTokensCache, userSessionIdsSet);

		}
	}

	public String getTokenSessionCache() {
		return tokenSessionCache;
	}

	public void setTokenSessionCache(String tokenSessionCache) {
		this.tokenSessionCache = tokenSessionCache;
	}

	public String getUserTokensCache() {
		return userTokensCache;
	}

	public void setUserTokensCache(String userTokensCache) {
		this.userTokensCache = userTokensCache;
	}

}
