package cn.com.xinxin.portal.web.shiro.filter;

import cn.com.xinxin.oplog.client.Oplog;
import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.oplog.client.request.OplogReq;
import cn.com.xinxin.portal.biz.service.UserService;
import cn.com.xinxin.portal.repository.model.ResourceDO;
import cn.com.xinxin.portal.repository.model.RoleDO;
import cn.com.xinxin.portal.repository.model.UserDO;
import cn.com.xinxin.portal.session.model.PortalUser;
import cn.com.xinxin.portal.session.repository.UserAclSessionRepository;
import cn.com.xinxin.portal.session.utils.HttpRequestUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dengyunhui on 2018/5/11
 **/
public class PortalLoginFilter extends FormAuthenticationFilter {

    private UserService userService;

    private UserAclSessionRepository userAclSessionRepository;

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        String userNo = (String) token.getPrincipal();

        ShiroHttpServletRequest httpServletRequest = (ShiroHttpServletRequest) request;

        String ipAddress = HttpRequestUtil.getIpAddress(httpServletRequest);
        String device = HttpRequestUtil.getRequestDevice(httpServletRequest);
        String sessionId = httpServletRequest.getSession().getId();

        UserDO user = userService.findByUserNo(userNo);

        PortalUser portalUser = new PortalUser();
        portalUser.setAccount(user.getAccount());
        portalUser.setId(user.getId());
        portalUser.setName(user.getName());
        portalUser.setNo(user.getNo());
        portalUser.setUserType(user.getUserType());
        portalUser.setIp(ipAddress);
        portalUser.setDevice(device);

        List<RoleDO> roleDOList = userService.findRoles(userNo);
        if (!CollectionUtils.isEmpty(roleDOList)){
            Set<String> roleCodes = new HashSet<>(roleDOList.size());
            roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
            portalUser.setRoles(roleCodes);
        }

        List<ResourceDO> resourceDOS = userService.findResources(userNo);
        if (!CollectionUtils.isEmpty(resourceDOS)){
            Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
            resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getUrl()));
            portalUser.setStringPermissions(permissionUrls);
        }

        // 读取session
        Session session = userAclSessionRepository.getPortalUserLoginSession(sessionId);
        session.setAttribute(userNo,portalUser);

        // 更新session
        userAclSessionRepository.setPortalUserSession(sessionId, session);

        // 设置用户信息
        session.setAttribute(userNo,portalUser);

        userAclSessionRepository.setPortalUserByUserNo(userNo, portalUser);

        OplogReq oplogProxyReq = new OplogReq(
            AppProductEnum.XPORTAL_LOGIN,
            OperationTypeEnum.LOGIN,
            portalUser.getAccount(),
            portalUser.getName(),
            portalUser.getId()+"",
            "onLoginSuccess",
            ipAddress,
            device,
            new Date()
        );

        Oplog.log(oplogProxyReq);

        return super.onLoginSuccess(token, subject, request, response);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public UserAclSessionRepository getUserAclSessionRepository() {
        return userAclSessionRepository;
    }

    public void setUserAclSessionRepository(UserAclSessionRepository userAclSessionRepository) {
        this.userAclSessionRepository = userAclSessionRepository;
    }
}
