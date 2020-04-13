package cn.com.xinxin.sass.biz.convert;

import cn.com.xinxin.sass.api.model.UserDTO;
import cn.com.xinxin.sass.repository.model.UserDO;
import com.xinxinfinance.commons.util.BaseConvert;

/**
 * @author: zhouyang
 * @created: 21/05/2018.
 * @description:
 */
public class UserConvert {

    public static UserDTO convert2UserDTO(final UserDO userDO){

        UserDTO userDTO = BaseConvert.convert(userDO, UserDTO.class);

        // 额外的其他转换

        return userDTO;
    }
}
