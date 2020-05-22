package cn.com.xinxin.sass.sal.model.wechatwork;

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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信部门BO
 */
public class WeChatWorkDepartmentBO {
    /**
     * 创建的部门id
     */
    @JSONField(name = "id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @JSONField(name = "name")
    private String departmentName;

    /**
     * 英文名称
     */
    @JSONField(name = "name_en")
    private String departmentEnglishName;

    /**
     * 父部门id。根部门为1
     */
    @JSONField(name = "parentid")
    private Long departmentParentId;

    /**
     * 在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
     */
    @JSONField(name = "order")
    private Long departmentOrder;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentEnglishName() {
        return departmentEnglishName;
    }

    public void setDepartmentEnglishName(String departmentEnglishName) {
        this.departmentEnglishName = departmentEnglishName;
    }

    public Long getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(Long departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public Long getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(Long departmentOrder) {
        this.departmentOrder = departmentOrder;
    }

    @Override
    public String toString() {
        return "WeChatWorkDepartmentBO{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentEnglishName='" + departmentEnglishName + '\'' +
                ", departmentParentId=" + departmentParentId +
                ", departmentOrder=" + departmentOrder +
                '}';
    }
}
