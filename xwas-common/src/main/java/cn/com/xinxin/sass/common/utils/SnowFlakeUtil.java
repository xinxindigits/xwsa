package cn.com.xinxin.sass.common.utils;

import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.idgen.SnowFakeIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * added by zhangwei 2018/07/31
 * 64位ID (1(不用)+41(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 * <p>
 *     改成调用{@link com.xinxinfinance.commons.idgen.SnowFakeIdGenerator}
 * </p>
 */
public class SnowFlakeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlakeUtil.class);

    private SnowFlakeUtil(){}

    /**默认前缀*/
    public static final String TK_PREFIX = "TK";

    /**
     * 生成流水号
     * @return long
     */
    public static long generateLongId(){
        long start = System.currentTimeMillis();
        LOGGER.debug("生成流水号开始...");
        try {
            long id = SnowFakeIdGenerator.getInstance().generateLongId();
            long end = System.currentTimeMillis();
            LOGGER.debug("生成流水号结束，id = {}，耗时：{} ms", id, end - start);
            return id;
        }catch (Exception e){
            LOGGER.error("雪花算法生成流水号异常！",e);
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR,"生成流水号异常！");
        }
    }

    /**
     * 生成流水号
     * @return String
     */
    public static String generateId(){
        return String.valueOf(generateLongId());
    }

    /**
     * 生成流水号
     * @param preFix 前缀
     * @return String
     */
    public static String generateId(String preFix){
        return StringUtils.isBlank(preFix) ? "" + generateId() : preFix + generateId();
    }


}
