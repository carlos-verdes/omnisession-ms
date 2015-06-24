package com.capgemini.omnichannel.omnisession.model.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${omnisession.tokenSessionCache}")
	private String tokenSessionCache;

	@Value("${omnisession.userTokensCache}")
	private String userTokensCache;

	@Autowired
	private CacheService cacheService;

	@Override
	public SessionDTO getEntityById(String id) {
		SessionDTO sessionDTO = cacheService.get(id, tokenSessionCache);

		if (sessionDTO != null) {
			touchOmnisession(sessionDTO);
		}

		return sessionDTO;
	}

	@Override
	public SessionDTO updateEntity(String id, SessionDTO value) {
		logger.debug(String.format("updating entity, id:value -->  %s: %s", id, value));

		// update omnisession
		updateOmnisession(value);

		// update current session
		cacheService.put(id, tokenSessionCache, value);

		return value;
	}

	@Override
	public SessionDTO insertEntity(String id, SessionDTO value) {
		logger.debug(String.format("inserting entity, id:value -->  %s: %s", id, value));

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
	public void doWithSession(String token, Consumer<SessionDTO> doWithSession) {

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			SessionDTO sessionDTO = this.getEntityById(token);

			// touch omnisession to update idle timeouts
			touchOmnisession(sessionDTO);

			doWithSession.accept(sessionDTO);

		}

	}

	@Override
	public List<SessionDTO> getRelatedSessions(String token) {

		final List<SessionDTO> relatedSessions = new ArrayList<SessionDTO>();

		// if session exist
		SessionDTO sessionDTO = this.getEntityById(token);
		if (sessionDTO != null) {
			Set<String> userSessionIdSet = this.getFromOmnisessionOfDefault(sessionDTO);

			Iterator<String> iter = userSessionIdSet.iterator();
			while (iter.hasNext()) {
				String relatedToken = iter.next();

				if (!relatedToken.equals(token)) {
					SessionDTO relatedSession = this.getEntityById(relatedToken);

					// if exist in cache
					if (relatedSession != null) {
						// add to results
						relatedSessions.add(relatedSession);
					} else {
						// remove from omnisession
						iter.remove();
					}

				}
			}
			// update omnisession (some session could be expired)
			updateOmnisessionCache(sessionDTO, userSessionIdSet);

		}

		return relatedSessions;
	}

	@Override
	public <T> T getSessionData(String token, String key, Class<? extends T> clazz) {
		T result = null;

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			SessionDTO sessionDTO = this.getEntityById(token);

			result = sessionDTO.getData(key, clazz);
		}

		return result;
	}

	@Override
	public <T> void updateSessionData(String token, String key, T data) {

		if (cacheService.containsKey(token, this.tokenSessionCache)) {
			// get the session
			SessionDTO sessionDTO = this.getEntityById(token);

			// update session
			sessionDTO.putData(key, data);

		}

	}

	private Set<String> getFromOmnisessionOfDefault(SessionDTO sessionDTO) {
		String userId = sessionDTO.getUserId();
		return this.cacheService.getOrDefault(userId, this.userTokensCache, new HashSet<String>());
	}

	private void touchOmnisession(SessionDTO sessionDTO) {
		// just check the omnisession to update timeouts
		if (sessionDTO != null) {
			getFromOmnisessionOfDefault(sessionDTO);

		}

	}

	private void updateOmnisession(SessionDTO sessionDTO) {

		if (sessionDTO != null) {
			// get user sessions (or default)
			Set<String> userSessionIdsSet = getFromOmnisessionOfDefault(sessionDTO);

			// add current session
			userSessionIdsSet.add(sessionDTO.getToken());

			// update cacheO
			updateOmnisessionCache(sessionDTO, userSessionIdsSet);

		}

	}

	private void updateOmnisessionCache(SessionDTO sessionDTO, Set<String> userSessionIdsSet) {
		String userId = sessionDTO.getUserId();
		// put in omnisession in cache
		this.cacheService.put(userId, this.userTokensCache, userSessionIdsSet);
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

	@Override
	public Class<? extends SessionDTO> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
