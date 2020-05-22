package cn.com.xinxin.sass.web.convert;

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

import cn.com.xinxin.sass.common.enums.GenderTypeEnums;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.web.vo.CustomerVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/28.
 * @updater:
 * @description: 客户转换类
 */
public class CustomerConvert {

    /**
     * 将customerDO转化成CustomerVO
     * @param customerDO 客户DO
     * @return CustomerVO
     */
    public static CustomerVO convert2CustomerVO(CustomerDO customerDO) {
        CustomerVO customerVO = new CustomerVO();
        if (null == customerDO) {
            return customerVO;
        }
        customerVO.setId(customerDO.getId());
        customerVO.setMemberUserId(customerDO.getMemberUserId());
        customerVO.setUserId(customerDO.getUserId());
        customerVO.setCustomerName(customerDO.getCustomerName());
        customerVO.setCustomerType(customerDO.getCustomerType());
        if (null != customerDO.getGender()) {
            customerVO.setGender(GenderTypeEnums.getEnumByNum(String.valueOf(customerDO.getGender())).getDesc());
        }
        customerVO.setUnionId(customerDO.getUnionId());
        customerVO.setCustomerPosition(customerDO.getCustomerPosition());
        customerVO.setCorpName(customerDO.getCorpName());
        customerVO.setCorpFullName(customerDO.getCorpFullName());
        customerVO.setExternalProfile(customerDO.getExternalProfile());
        customerVO.setFollowUser(customerDO.getFollowUser());
        customerVO.setStatus(customerDO.getStatus());
        customerVO.setAvatar(customerDO.getAvatar());
        customerVO.setCreatedTime(customerDO.getGmtCreated().getTime());
        return customerVO;
    }

    /**
     * 将customerDOList转化成CustomerVOList
     * @param customerDOS 客户DO
     * @return customerVOS
     */
    public static List<CustomerVO> convert2CustomerVOList(List<CustomerDO> customerDOS) {
        List<CustomerVO> customerVOS = new ArrayList<>();
        customerDOS.forEach(c -> customerVOS.add(convert2CustomerVO(c)));
        return customerVOS;
    }
}
