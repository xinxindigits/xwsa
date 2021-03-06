package cn.com.xinxin.sass.job.test.service;


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

import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.common.enums.ResourceTypeEnums;
import cn.com.xinxin.sass.job.test.base.SpringBaseTest;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class ResourceServiceTest extends SpringBaseTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Test
    @Ignore
    public void testCreateResource(){
        ResourceDO resourceDO = new ResourceDO();
        resourceDO.setCode("2001");
        resourceDO.setName("客户服务");
        resourceDO.setParentId(2l);
        resourceDO.setRoot(false);
        resourceDO.setUrl("/credit/custom");
        resourceDO.setResourceType(ResourceTypeEnums.MENU_TYPE.name());

        ResourceDO resourceDO2 = new ResourceDO();
        resourceDO2.setCode("2002");
        resourceDO2.setName("账户服务");
        resourceDO2.setParentId(2l);
        resourceDO2.setRoot(false);
        resourceDO2.setUrl("/credit/account");
        resourceDO2.setResourceType(ResourceTypeEnums.MENU_TYPE.name());

        ResourceDO resourceDO3 = new ResourceDO();
        resourceDO3.setCode("2003");
        resourceDO3.setName("微信用服务");
        resourceDO3.setParentId(2l);
        resourceDO3.setRoot(false);
        resourceDO3.setUrl("/credit/microcredit");
        resourceDO3.setResourceType(ResourceTypeEnums.MENU_TYPE.name());

        resourceService.createResource(resourceDO);
        resourceService.createResource(resourceDO2);
        resourceService.createResource(resourceDO3);
    }

    @Test
    @Ignore
    public void testFindChildMenuByParentId(){
        List<ResourceDO> resourceDOS = resourceService.findChildMenuByParentId(1l);
        Assert.assertNotNull(resourceDOS);
    }

    @Test
    @Ignore
    public void test(){
        String resourceIdsStr = "5,25,27,29,45,47,55";

        List<RoleResourceDO> roleResources = new ArrayList<>();
        if (!StringUtils.isEmpty(resourceIdsStr)) {
            String[] resourceCodes = resourceIdsStr.split(",");
            List<Long> ids = new ArrayList<>();
            for (String resourceCode : resourceCodes) {
                if (!StringUtils.isEmpty(resourceCode)) {
                    ids.add(Long.parseLong(resourceCode));
                }
            }

            List<ResourceDO> resourceDOS = resourceService.findResourcesByIds(ids);

            List<Long> parentIds = resourceDOS.stream().map(ResourceDO::getId).collect(toList());
            List<ResourceDO> functionDOS = resourceService.findChildrenByParentIds(parentIds);
            if (!CollectionUtils.isEmpty(functionDOS)){
                resourceDOS.addAll(functionDOS);
            }

            for (ResourceDO resourceDO : resourceDOS) {
                RoleResourceDO roleResourceDO = new RoleResourceDO();
                roleResourceDO.setResourceName(resourceDO.getName());
                roleResourceDO.setResourceCode(resourceDO.getCode());
                roleResourceDO.setRoleName("管理员");
                roleResourceDO.setRoleCode("admin001");
                roleResourceDO.setGmtCreator("aggz1004");

                roleResources.add(roleResourceDO);
            }
        }

        if (!CollectionUtils.isEmpty(roleResources)) {
            roleResourceService.createRoleResources(roleResources);
        }
    }

}
