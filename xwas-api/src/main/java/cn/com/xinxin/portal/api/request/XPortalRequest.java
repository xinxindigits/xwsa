package cn.com.xinxin.portal.api.request;

import cn.com.xinxin.portal.api.base.ToString;

/**
 * Created by helloyy on 19/01/2018.
 */
public class XPortalRequest extends ToString {

    public String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
