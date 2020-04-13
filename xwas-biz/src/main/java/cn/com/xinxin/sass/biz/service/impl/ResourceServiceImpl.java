package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.dao.ResourceMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public ResourceDO createResource(ResourceDO resourceDO) {
        resourceMapper.insertSelective(resourceDO);
        return resourceDO;
    }

    @Override
    public int updateResource(ResourceDO resourceDO) {
        return resourceMapper.updateByPrimaryKeySelective(resourceDO);
    }

    @Override
    public void deleteResource(long id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ResourceDO> findResources(List<String> codes) {
        return resourceMapper.findResources(codes);
    }

    @Override
    public List<ResourceDO> findResourcesByIds(List<Long> ids) {
        return resourceMapper.findResourcesByIds(ids);
    }

    @Override
    public List<ResourceDO> findChildMenuByParentId(Long parentId) {
        return resourceMapper.findChildMenuByParentId(parentId);
    }

    @Override
    public List<ResourceDO> findChildrenByParentIds(List<Long> parentId) {
        return resourceMapper.findChildrenByParentIds(parentId);
    }

    @Override
    public List<ResourceDO> findRootResources() {
        return resourceMapper.findRootResources();
    }

    @Override
    public List<Long> findResourceIds(List<String> codes) {
        return resourceMapper.findResourceIds(codes);
    }

    @Override
    public List<ResourceDO> findAllResources() {
        return resourceMapper.findAllResources();
    }

    @Override
    public ResourceDO findById(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteById(Long id) {
        int n = resourceMapper.deleteByPrimaryKey(id);
        return n == 1;
    }

    @Override
    public Page<ResourceDO> findByConditionPage(Page page, ResourceDO condition) {
        com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<ResourceDO> resourceDOS = resourceMapper.findByCondition(condition);
        Page<ResourceDO> result = new Page<>();
        result.setTotal(page1.getTotal());
        result.setRows(resourceDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());

        return result;
    }

    @Override
    public ResourceDO findByResourceCode(String code) {
        return resourceMapper.findByResourceCode(code);
    }
}
