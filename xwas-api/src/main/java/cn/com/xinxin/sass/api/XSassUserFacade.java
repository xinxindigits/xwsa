package cn.com.xinxin.sass.api;

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

import cn.com.xinxin.sass.api.model.UserDTO;
import cn.com.xinxin.sass.api.enums.SassResultCodeEnum;
import com.xinxinfinance.commons.api.result.generic.SingleResult;

/**
 * Created by dengyunhui on 2018/5/21
 **/
public interface XSassUserFacade {

    /**
     * 根据sessionId获取当前登录的用户信息
     * @param sessionId
     * @return
     */
    SingleResult<SassResultCodeEnum,UserDTO> getLoginUser(String sessionId);

    /**
     * 当前登录用户对某个路径是否有权限
     * @param sessionId
     * @param url
     * @return
     */
    SingleResult<SassResultCodeEnum,Boolean> hasPermission(String sessionId, String url);
}