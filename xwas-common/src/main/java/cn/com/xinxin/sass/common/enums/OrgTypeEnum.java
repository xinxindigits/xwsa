package cn.com.xinxin.sass.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yanghaoxian
 * @created: 2020/5/7.
 * @updater:
 * @description:
 */
public enum OrgTypeEnum {

    COMP("COMP","公司"),
    DEPART("DEPART","部门"),
    GROUP("GROUP","小组"),
    ;

    private static ConcurrentHashMap<String, OrgTypeEnum> concurrentHashMap = new ConcurrentHashMap<>(8);

    static {
        Arrays.stream(OrgTypeEnum.values()).forEach(orgTypeEnum -> {
            concurrentHashMap.put(orgTypeEnum.getCode(),orgTypeEnum);
        });
    }

    public static OrgTypeEnum getEnumByCode(String code){
        if(!StringUtils.isEmpty(code)){
            return concurrentHashMap.get(code);
        }
        return null;
    }

    private String code;

    private String desc;

    OrgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
