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
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.repository.model.bo.TenantDataSyncLogBO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步任务日志数据库服务
 */
public interface TenantDataSyncLogService {

    /**
     * 根据id进行更新
     * @param tenantDataSyncLogDO 记录
     * @return 更新记录成功的条数
     */
    int updateById(TenantDataSyncLogDO tenantDataSyncLogDO);

    /**
     * 插入记录并将生成的id保存到orgDataSyncLogDO的id字段
     * @param tenantDataSyncLogDO 机构同步任务日志
     * @return 插入记录成功的条数
     */
    int insertReturnId(TenantDataSyncLogDO tenantDataSyncLogDO);


    /**
     * 分页查询租户任务执行日志
     * @param tenantDataSyncLogBO 参数
     * @param page 分页参数
     * @return 日志
     */
    PageResultVO<TenantDataSyncLogDO> selectRecordSPage(TenantDataSyncLogBO tenantDataSyncLogBO, PageResultVO page);
}
