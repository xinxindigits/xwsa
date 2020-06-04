package cn.com.xinxin.sass.biz.service;

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

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 机构基础信息配置
 */
public interface TenantBaseInfoService {

    /**
     * 通过机构id查询
     * @param tenantId 机构id
     * @return 机构基础信息
     */
    TenantBaseInfoDO selectByTenantId(String tenantId);

    boolean createOrgBaseInfo(TenantBaseInfoDO tenantBaseInfoDO);

    boolean updateByOrgId(TenantBaseInfoDO tenantBaseInfoDO);

    int deleteByCodes(List<String> codes);

    /**
     *
     * @return
     */
    PageResultVO<TenantBaseInfoDO> findByCondition(PageResultVO page, TenantBaseInfoDO condition);

    /**
     *
     * @return
     */
    List<TenantBaseInfoDO> queryAllTenants();

}
