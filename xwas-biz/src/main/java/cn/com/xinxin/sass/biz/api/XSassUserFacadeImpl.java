package cn.com.xinxin.sass.biz.api;

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
