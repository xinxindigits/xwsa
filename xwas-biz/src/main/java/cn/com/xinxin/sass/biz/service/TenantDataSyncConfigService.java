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

import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步配置信息DB服务
 */
public interface TenantDataSyncConfigService {

    /**
     * 通过机构id和任务类型查询记录
     * @param orgId 机构id
     * @param taskType 任务类型
     * @return 机构同步配置信息
     */
    TenantDataSyncConfigDO selectByOrgIdAndTaskType(String orgId, String taskType);

    /**
     * 根据id更新记录
     * @param tenantDataSyncConfigDO 记录
     * @return 更新成功的条数
     */
    int updateById(TenantDataSyncConfigDO tenantDataSyncConfigDO);

    /**
     * 上锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    int updateLockByTenantIdAndTaskType(String tenantId, String taskType);

    /**
     * 解锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    int updateUnLockByTenantIdAndTaskType(String tenantId, String taskType);

    /**
     * 查询所有未被删除的记录
     * @return 未被删除的记录
     */
    List<TenantDataSyncConfigDO> queryValidRecord();

    /**
     * 通过机构id查询记录
     * @param tenantId
     * @return
     */
    List<TenantDataSyncConfigDO> selectByTenantId(String tenantId);

    /**
     * 插入配置并且初始化任务
     * @param tenantDataSyncConfigDO 配置
     * @return 成功插入条数
     */
    int insert(TenantDataSyncConfigDO tenantDataSyncConfigDO);

    /**
     * 通过id查询记录
     * @param id 数据库主键
     * @return 记录
     */
    TenantDataSyncConfigDO selectById(Long id);
}
