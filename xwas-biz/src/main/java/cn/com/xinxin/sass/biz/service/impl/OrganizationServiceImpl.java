package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.dao.OrganizationMapper;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public OrganizationDO createOrganization(OrganizationDO organizationDO) {
        organizationMapper.insertSelective(organizationDO);
        return organizationDO;
    }

    @Override
    public OrganizationDO updateOrganization(OrganizationDO organizationDO) {
        organizationMapper.updateByPrimaryKeySelective(organizationDO);
        return organizationDO;
    }

    @Override
    public OrganizationDO findById(Long id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<OrganizationDO> findByCondition(Page page, OrganizationDO condition) {
        com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<OrganizationDO> organizationDOS = organizationMapper.findByCondition(condition);

        Page<OrganizationDO> result = new Page<>();
        result.setTotal(page1.getTotal());
        result.setRows(organizationDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());

        return result;
    }

    @Override
    public Boolean deleteById(Long id) {
        int n = organizationMapper.deleteByPrimaryKey(id);
        return n == 1;
    }

}
