package cn.com.xinxin.portal.session.interceptor;

import cn.com.xinxin.portal.session.model.PortalUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: zhouyang
 * @created: 10/04/2020.
 * @updater:
 * @description:
 */
public class KickoutSessionControlFilter extends AccessControlFilter {


    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

    //踢出后到的地址
    private String kickoutUrl;
    //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private boolean kickoutAfter = false;
    //同一个帐号最大会话数 默认1
    private int maxSession = 1;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {

        logger.info("KickoutSessionControlFilter==== isAccessAllowed === {}", kickoutUrl);

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {


        logger.info("KickoutSessionControlFilter==== onAccessDenied === {}", kickoutUrl);

        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        //PortalUser user = (PortalUser)subject.getPrincipal();
        //String username = user.getAccount();
        String username = (String) subject.getPrincipal();


        logger.info("KickoutSessionControlFilter==== onAccessDenied ===username ==== {}", username);
        Serializable sessionId = session.getId();

        // 同步控制
        Deque<Serializable> deque = cache.get(username);
        if(deque == null || CollectionUtils.isEmpty(deque)) {
            // 如果队列为空，则放入队列
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
            cache.put(username, deque);
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
            logger.info("用户登录人数超过限制, 重定向到{}", kickoutUrl);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;

        }
        return true;
    }

}
