package cn.com.xinxin.sass.biz.tenant.ops;




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

import cn.com.xinxin.sass.biz.tenant.TenantIdContext;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 21/05/2020.
 * @updater:
 * @description: 运营端后台租户隔离的实现
 */
public class SassOpsTenantHandler implements TenantHandler {


    /**
     * 不需要租户隔离的全局表
     */
    private static final List<String> IGNORE_TENANT_TABLES = Lists.newArrayList("user",
            "tenant_sync_config","qrtz_blob_triggers","qrtz_calendars","qrtz_cron_triggers","qrtz_fired_triggers",
            "qrtz_job_details","qrtz_locks","qrtz_paused_trigger_grps","qrtz_scheduler_state","qrtz_simple_triggers",
            "qrtz_simprop_triggers","qrtz_triggers","auths","tenant");

    @Override
    public Expression getTenantId(boolean where) {
        return new StringValue(TenantIdContext.get());
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant";
    }

    @Override
    public boolean doTableFilter(String tableName) {
        Boolean match =  IGNORE_TENANT_TABLES.stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
        return match;
    }

}
