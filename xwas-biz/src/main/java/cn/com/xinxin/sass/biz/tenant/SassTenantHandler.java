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


    private static final List<String> IGNORE_TENANT_TABLES = Lists.newArrayList("user","tenant_sync_config");

    @Override
    public Expression getTenantId(boolean where) {
        return new StringValue(TenantIdContext.get());
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean doTableFilter(String tableName) {
        Boolean match =  IGNORE_TENANT_TABLES.stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
        return match;
    }

}
