package cn.com.xinxin.sass.biz.convert;

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

import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门转换类
 */
public class DepartmentConvert {

    /**
     * 将WeChatWorkDepartmentBO转化为DepartmentReceivedDO
     * @param weChatWorkDepartmentBO 企业微信部门BO
     * @param taskId 任务id
     * @param orgId 机构id
     * @return DepartmentReceivedDO
     */
    public static DepartmentReceivedDO convert2DepartmentReceivedDO(WeChatWorkDepartmentBO weChatWorkDepartmentBO,
                                                                    String taskId, String orgId) {
        DepartmentReceivedDO departmentReceivedDO = new DepartmentReceivedDO();
        departmentReceivedDO.setTaskId(taskId);
        departmentReceivedDO.setTenantId(orgId);
        departmentReceivedDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        if (null != weChatWorkDepartmentBO) {
            departmentReceivedDO.setDepartmentId(weChatWorkDepartmentBO.getDepartmentId());
            departmentReceivedDO.setDepartmentName(weChatWorkDepartmentBO.getDepartmentName());
            departmentReceivedDO.setDepartmentOrder(weChatWorkDepartmentBO.getDepartmentOrder());
            departmentReceivedDO.setEnglishName(weChatWorkDepartmentBO.getDepartmentEnglishName());
            departmentReceivedDO.setParentId(weChatWorkDepartmentBO.getDepartmentParentId());
        }
        return departmentReceivedDO;
    }

    /**
     * 将WeChatWorkDepartmentBOList转化为DepartmentReceivedDOList
     * @param weChatWorkDepartmentBOList 企业微信部门BO列表
     * @param taskId 任务id
     * @param orgId 机构id
     * @return DepartmentReceivedDOList
     */
    public static List<DepartmentReceivedDO> convert2DepartmentReceivedDOList(
            List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOList, String taskId, String orgId) {
        List<DepartmentReceivedDO> departmentReceivedDOS = new ArrayList<>();
        weChatWorkDepartmentBOList.forEach(d -> departmentReceivedDOS.add(convert2DepartmentReceivedDO(d, taskId, orgId)));
        return departmentReceivedDOS;
    }
}
