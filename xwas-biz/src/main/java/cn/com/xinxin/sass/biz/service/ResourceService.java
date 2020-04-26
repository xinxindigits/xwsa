package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface ResourceService {

    int createResource(ResourceDO resourceDO);

    int updateResource(ResourceDO resourceDO);

    void deleteResource(long id);

    List<ResourceDO> findResources(List<String> codes);

    List<ResourceDO> findResourcesByIds(List<Long> ids);

    List<ResourceDO> findChildMenuByParentId(Long parentId);

    List<ResourceDO> findChildrenByParentIds(List<Long> parentId);

    List<ResourceDO> findRootResources();

    List<Long> findResourceIds(List<String> codes);

    List<ResourceDO> findAllResources();

    ResourceDO findById(Long id);

    boolean deleteById(Long id);

    PageResultVO<ResourceDO> findByConditionPage(PageResultVO pageVO, ResourceDO condition);

    ResourceDO findByResourceCode(String code);


    List<ResourceDO> queryResourceTrees(String code,
                                        String type,
                                        String parentId);
}
