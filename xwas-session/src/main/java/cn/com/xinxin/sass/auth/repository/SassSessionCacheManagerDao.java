package cn.com.xinxin.sass.auth.repository;

import cn.com.xinxin.sass.auth.constant.SessionCacheConstants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description:
 */
public class SassSessionCacheManagerDao extends AbstractSessionDAO {

    private static final Logger log = LoggerFactory.getLogger(SassSessionCacheManagerDao.class);

    private RedisTemplate sessionRedisTemplate;

    private String getKey(String originalKey) {
        return SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + originalKey;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        try {
            sessionRedisTemplate.opsForValue().set(getKey(session.getId().toString()),
                    session,SessionCacheConstants.DEFAULT_SESSION_TIME_OUT, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("PortalSessionRepository.update occurs exception:\n[]",e);
        }
    }
    @Override
    public void delete(Session session) {
        try {
            String key = getKey(session.getId().toString());
            sessionRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("PortalSessionRepository.delete occurs exception:\n[]",e);
        }
    }


    @Override
    public Collection<Session> getActiveSessions() {
        List<Session> values = (List<Session>) sessionRedisTemplate.opsForValue().get(
                SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + "*");
        Set<Session> sessions = new HashSet<>();
        sessions.addAll(values);

        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        try {
            sessionRedisTemplate.opsForValue().set(getKey(session.getId().toString()),
                    session, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("PortalSessionRepository.doCreate occurs exception:\n[]",e);
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        try {
            session = (Session) sessionRedisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            log.error("PortalSessionRepository.doReadSession occurs exception:\n[]",e);
        }
        return session;
    }

    public RedisTemplate getSessionRedisTemplate() {
        return sessionRedisTemplate;
    }

    public void setSessionRedisTemplate(RedisTemplate sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

}
