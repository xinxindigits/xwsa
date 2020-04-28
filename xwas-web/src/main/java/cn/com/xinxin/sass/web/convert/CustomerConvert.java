package cn.com.xinxin.sass.web.convert;

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
        customerVO.setMemberUserId(customerDO.getMemberUserId());
        customerVO.setUserId(customerDO.getUserId());
        customerVO.setCustomerName(customerDO.getCustomerName());
        customerVO.setCustomerType(customerDO.getCustomerType());
        customerVO.setGender(GenderTypeEnums.getEnumByNum(String.valueOf(customerDO.getGender())).getDesc());
        customerVO.setUnionId(customerDO.getUnionId());
        customerVO.setCustomerPosition(customerDO.getCustomerPosition());
        customerVO.setCorpName(customerDO.getCorpName());
        customerVO.setCorpFullName(customerDO.getCorpFullName());
        customerVO.setExternalProfile(customerDO.getExternalProfile());
        customerVO.setFollowUser(customerDO.getFollowUser());
        customerVO.setStatus(customerDO.getStatus());
        customerVO.setAvatar(customerDO.getAvatar());
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
