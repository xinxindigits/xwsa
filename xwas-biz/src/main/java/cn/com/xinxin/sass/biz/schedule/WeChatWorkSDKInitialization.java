package cn.com.xinxin.sass.biz.schedule;

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

import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.TenantBaseInfoDOMapper;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import com.tencent.wework.ChattingRecordsUtils;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuhangzhou
 * @created: 2020/5/14.
 * @updater:
 * @description: 企业微信sdk初始化类
 */
@Service
@Deprecated
public class WeChatWorkSDKInitialization {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkSDKInitialization.class);

    private static Map<String, Long> sdkMap = new HashMap<>();

    private TenantBaseInfoDOMapper tenantBaseInfoDOMapper;

    public WeChatWorkSDKInitialization(TenantBaseInfoDOMapper tenantBaseInfoDOMapper) {
        this.tenantBaseInfoDOMapper = tenantBaseInfoDOMapper;
    }

    //@PostConstruct
    public void initSDK() {
        LOGGER.info("初始化企业微信sdk");
        List<TenantBaseInfoDO> tenantBaseInfoDOS = tenantBaseInfoDOMapper.listAlltenants();
        if (CollectionUtils.isNotEmpty(tenantBaseInfoDOS)) {
            tenantBaseInfoDOS.forEach(t -> {
                LOGGER.info("初始化租户[{}]企业微信sdk", t.getCorpId());
                if (StringUtils.isNotBlank(t.getChatRecordSecret())) {
                    try {
                        sdkMap.put(t.getCorpId(), ChattingRecordsUtils.initSdk(t.getCorpId(), t.getChatRecordSecret()));
                    } catch (Throwable throwable) {
                        LOGGER.error("初始化租户[{}]企业微信sdk异常", t.getCorpId(), throwable);
                    }

                }
            });
        }
    }

    //@PreDestroy
    public void destroy() {
        LOGGER.info("销毁企业微信sdk");
        if (null != sdkMap) {
            for (Map.Entry<String, Long> entry : sdkMap.entrySet()) {
                LOGGER.info("销毁租户[{}]企业微信sdk", entry.getKey());
                try {
                    ChattingRecordsUtils.destorySdk(entry.getValue());
                } catch (Throwable throwable) {
                    LOGGER.error("初始化租户[{}]企业微信sdk异常", entry.getKey(), throwable);
                }
            }
        }
    }

    public Long getSdk(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        if (!sdkMap.containsKey(key)) {
            TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoDOMapper.selectByCorpId(key);
            if (null == tenantBaseInfoDO || StringUtils.isBlank(tenantBaseInfoDO.getChatRecordSecret())) {
                LOGGER.error("无法通过corpId[{}]获取租户配置信息", key);
                throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "无法通过corpId获取租户配置信息");
            }
            LOGGER.info("初始化租户[{}]企业微信sdk", key);
            try {
                long sdk = ChattingRecordsUtils.initSdk(key, tenantBaseInfoDO.getChatRecordSecret());
                sdkMap.put(key, sdk);
                return sdk;
            } catch (Throwable throwable) {
                LOGGER.error("初始化租户[{}]企业微信sdk异常", key, throwable);
            }
        }

        return sdkMap.get(key);
    }
}
