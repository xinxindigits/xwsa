package cn.com.xinxin.sass.web.shiro.filter;

import cn.com.xinxin.oplog.client.Oplog;
import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.oplog.client.request.OplogReq;
import cn.com.xinxin.sass.session.model.PortalUser;
import cn.com.xinxin.sass.session.repository.UserAclSessionRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by dengyunhui on 2018/5/11
 **/
public class PortalLogoutFilter extends LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(PortalLogoutFilter.class);

    @Autowired
    private UserAclSessionRepository userAclSessionRepository;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String sessionId = httpServletRequest.getRequestedSessionId();

        if (StringUtils.isBlank(sessionId)){
            return super.preHandle(request,response);
        }

        try {
            PortalUser portalUser = userAclSessionRepository.getPortalUserBySessionId(sessionId);
            userAclSessionRepository.cleanPortalUser(portalUser.getAccount());
            OplogReq oplogProxyReq = new OplogReq(
                    AppProductEnum.XPORTAL_LOGOUT,
                    OperationTypeEnum.LOGOUT,
                    portalUser.getAccount(),
                    portalUser.getName(),
                    portalUser.getId()+"",
                    "logout",
                    portalUser.getIp(),
                    portalUser.getDevice(),
                    new Date()
            );

            Oplog.log(oplogProxyReq);
        }catch (Exception e){
            log.error("logout出错",e);
        }

        return super.preHandle(request, response);
    }
}
