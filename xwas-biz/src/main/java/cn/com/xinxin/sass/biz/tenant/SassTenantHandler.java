package cn.com.xinxin.sass.biz.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * @author: zhouyang
 * @created: 21/05/2020.
 * @updater:
 * @description:
 */
public class SassTenantHandler implements TenantHandler {


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
        return !"user".equalsIgnoreCase(tableName);
    }
}
