package cn.com.xinxin.sass.biz.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 21/05/2020.
 * @updater:
 * @description:
 */
public class SassTenantHandler implements TenantHandler {


    private static final List<String> IGNORE_TENANT_TABLES = Lists.newArrayList("user",
            "tenant_sync_config","qrtz_blob_triggers","qrtz_calendars","qrtz_cron_triggers","qrtz_fired_triggers",
            "qrtz_job_details","qrtz_locks","qrtz_paused_trigger_grps","qrtz_scheduler_state","qrtz_simple_triggers",
            "qrtz_simprop_triggers","qrtz_triggers");

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
