package cn.com.xinxin.portal.biz.api;

import cn.com.xinxin.portal.api.XPortalUserFacade;
import cn.com.xinxin.portal.api.model.UserDTO;
import cn.com.xinxin.portal.api.enums.XPortalResultCodeEnum;
import cn.com.xinxin.portal.biz.convert.UserConvert;
import cn.com.xinxin.portal.biz.service.UserService;
import cn.com.xinxin.portal.repository.model.UserDO;
import com.xinxinfinance.commons.api.result.generic.SingleResult;
import com.xinxinfinance.commons.api.result.support.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dengyunhui on 2018/5/21
 **/

class XPortalUserFacadeImpl implements XPortalUserFacade {

    @Autowired
    private UserService userService;

    @Override
    public SingleResult<XPortalResultCodeEnum, UserDTO> getLoginUser(String sessionId) {
        UserDO userDO = userService.getLoginUser(sessionId);
        UserDTO userDTO = UserConvert.convert2UserDTO(userDO);
        return Results.singleResult(XPortalResultCodeEnum.SUCCESS,userDTO);
    }

    @Override
    public SingleResult<XPortalResultCodeEnum, Boolean> hasPermission(String sessionId, String url) {
        Boolean result = userService.hasPermission(sessionId,url);

        return Results.singleResult(XPortalResultCodeEnum.SUCCESS,result);
    }
}
