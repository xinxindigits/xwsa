package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.ResourceMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import com.github.pagehelper.PageHelper;
import com.xinxinfinance.commons.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class ResourceServiceImpl implements ResourceService {


    private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public int createResource(ResourceDO resourceDO) {

        try {

            return resourceMapper.insertSelective(resourceDO);
        }catch (DuplicateKeyException ex){
            log.info("ResourceServiceImpl.createResource rs code = {} 重复",resourceDO.getCode());

            throw new BusinessException(SassBizResultCodeEnum.DATA_ALREADY_EXIST,"资源权限重复，请检查参数设置");

        }catch (Exception ex){

            throw new BusinessException(SassBizResultCodeEnum.FAIL,"资源权限创建失败，请检查参数后重试");

        }

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
    public PageResultVO<ResourceDO> findByConditionPage(PageResultVO page, ResourceDO condition) {
        com.github.pagehelper.Page pageHelper = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<ResourceDO> resourceDOS = resourceMapper.findByCondition(condition);
        PageResultVO<ResourceDO> result = new PageResultVO<>();
        result.setTotal(pageHelper.getTotal());
        result.setItems(resourceDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());
        return result;
    }

    @Override
    public ResourceDO findByResourceCode(String code) {
        return resourceMapper.findByResourceCode(code);
    }


    @Override
    public List<ResourceDO> queryResourceTrees(String code,
                                               String type,
                                               String parentId) {


        ResourceDO condition = new ResourceDO();

        if(!StringUtils.isEmpty(code)){
            condition.setCode(code);
        }

        if(!StringUtils.isEmpty(type)){
            condition.setResourceType(type);
        }

        if(!StringUtils.isEmpty(parentId)){
            condition.setParentId(Long.valueOf(parentId));
        }

        List<ResourceDO> resourceDOS = resourceMapper.findByCondition(condition);

        return resourceDOS;
    }
}
