package cn.com.xinxin.sass.auth.repository;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
 * @created: 16/06/2020.
 * @updater:
 * @description:
 */
public class SassSessionRedisDao extends AbstractSessionDAO {


    private static final Logger log = LoggerFactory.getLogger(SassSessionRedisDao.class);

    private RedisTemplate sessionRedisTemplate;

    private String getKey(String originalKey) {
        return SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + originalKey;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        try {
            sessionRedisTemplate.opsForValue().set(getKey(session.getId().toString()), session,
                    SessionCacheConstants.DEFAULT_SESSION_TIME_OUT, TimeUnit.MINUTES);
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
                SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + "*");
        Set<Session> sessions = new HashSet<>();
        sessions.addAll(values);

        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        try {
            sessionRedisTemplate.opsForValue().set(getKey(session.getId().toString()), session,
                    SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
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
