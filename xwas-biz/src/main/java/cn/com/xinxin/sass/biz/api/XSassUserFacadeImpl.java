package cn.com.xinxin.sass.biz.api;

import cn.com.xinxin.sass.api.XSassUserFacade;
import cn.com.xinxin.sass.api.model.UserDTO;
import cn.com.xinxin.sass.api.enums.SassResultCodeEnum;
import cn.com.xinxin.sass.biz.convert.UserConvert;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.UserDO;
import com.xinxinfinance.commons.api.result.generic.SingleResult;
import com.xinxinfinance.commons.api.result.support.Results;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dengyunhui on 2018/5/21
 **/

class XSassUserFacadeImpl implements XSassUserFacade {

    @Autowired
    private UserService userService;

    @Override
    public SingleResult<SassResultCodeEnum, UserDTO> getLoginUser(String sessionId) {
        UserDO userDO = userService.getLoginUser(sessionId);
        UserDTO userDTO = UserConvert.convert2UserDTO(userDO);
        return Results.singleResult(SassResultCodeEnum.SUCCESS,userDTO);
    }

    @Override
    public SingleResult<SassResultCodeEnum, Boolean> hasPermission(String sessionId, String url) {
        Boolean result = userService.hasPermission(sessionId,url);

        return Results.singleResult(SassResultCodeEnum.SUCCESS,result);
    }
}
