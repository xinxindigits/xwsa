package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.common.model.PageResultVO;
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
    public int createOrganization(OrganizationDO organizationDO) {
        return organizationMapper.insertSelective(organizationDO);
    }

    @Override
    public OrganizationDO updateOrganization(OrganizationDO organizationDO) {
        organizationMapper.updateByCodeSelective(organizationDO);
        return organizationDO;
    }

    @Override
    public OrganizationDO findById(Long id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResultVO<OrganizationDO> findByCondition(PageResultVO page, OrganizationDO condition) {
        com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<OrganizationDO> organizationDOS = organizationMapper.findByCondition(condition);

        PageResultVO<OrganizationDO> result = new PageResultVO<>();
        result.setTotal(page1.getTotal());
        result.setItems(organizationDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());

        return result;
    }

    @Override
    public Boolean deleteById(Long id) {
        int n = organizationMapper.deleteByPrimaryKey(id);
        return n == 1;
    }

    @Override
    public List<OrganizationDO> queryOrgList() {

        List<OrganizationDO> organizationDOS = this.organizationMapper.selectAllOrgs();

        return organizationDOS;
    }

    @Override
    public OrganizationDO findByCode(String code) {
        return organizationMapper.findByCode(code);
    }

    @Override
    public int deleteByCodes(List<String> codes) {
        return organizationMapper.deleteByCodes(codes);
    }
}
