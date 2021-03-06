package cn.com.xinxin.sass.biz.service.impl;

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

import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.DepartmentDOMapper;
import cn.com.xinxin.sass.repository.dao.MemberDOMapper;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 成员信息表数据库服务
 */
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberDOMapper memberDOMapper;

    private final DepartmentDOMapper departmentDOMapper;



    public MemberServiceImpl(final MemberDOMapper memberDOMapper,
                             final DepartmentDOMapper departmentDOMapper) {
        this.memberDOMapper = memberDOMapper;
        this.departmentDOMapper = departmentDOMapper;
    }

    /**
     * 通过机构id和用户id查询记录
     * @param orgId 机构id
     * @param userIdS 用户id
     * @return 成员列表
     */
    @Override
    public List<MemberDO> queryByOrgIdAndUserId(String orgId, List<String> userIdS) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id和用户id查询记录, orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, orgId不能为空");
        }
        if (CollectionUtils.isEmpty(userIdS)) {
            LOGGER.error("通过机构id和用户id查询记录, userId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, userId不能为空");
        }
        return memberDOMapper.queryByOrgIdAndUserId(orgId, userIdS);
    }

    /**
     * 批量插入
     * @param memberDOS 成员列表
     * @return 插入成功数量
     */
    @Override
    public int insertBatch(List<MemberDO> memberDOS) {
        if (CollectionUtils.isEmpty(memberDOS)) {
            LOGGER.warn("批量插入记录, memberDOS为空");
            return 0;
        }
        return memberDOMapper.insertBatch(memberDOS);
    }

    /**
     * 批量更新
     * @param memberDOS 成员列表
     * @return 更新成功数量
     */
    @Override
    public int updateBatchById(List<MemberDO> memberDOS) {
        if (CollectionUtils.isEmpty(memberDOS)) {
            LOGGER.warn("批量更新记录, memberDOS为空");
            return 0;
        }
        return memberDOMapper.updateBatchById(memberDOS);
    }


    @Override
    public PageResultVO<MemberDO> queryByDeptId(String deptId, PageResultVO page) {



        DepartmentDO departmentDO = this.departmentDOMapper.selectByDeptId(deptId);

        PageResultVO<MemberDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());


        if(departmentDO.getParentId().equals("0")){
            // 根结点，则查询所有的用户信息
            com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
            List<MemberDO> memberDOList = this.memberDOMapper.queryAllMembersByPage();
            result.setTotal(doPage.getTotal());
            result.setItems(memberDOList);
        }else{

            List<String> subDepartIds = this.departmentDOMapper.selectSubDeptsByDeptId(Lists.newArrayList(deptId));
            subDepartIds.add(deptId);

            com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
            List<MemberDO> memberDOList = this.memberDOMapper.queryDeptIdList(subDepartIds);
            result.setTotal(doPage.getTotal());
            result.setItems(memberDOList);
        }

        return result;
    }

    @Override
    public PageResultVO<MemberDO> queryMembersByPages(PageResultVO page) {


        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());


        List<MemberDO> memberDOList = this.memberDOMapper.queryAllMembersByPage();

        PageResultVO<MemberDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(memberDOList);

        return result;
    }

    @Override
    public MemberDO queryMemberDetailById(String memberId) {
        MemberDO memberDO = this.memberDOMapper.selectByPrimaryKey(Long.valueOf(memberId));
        return memberDO;
    }

    @Override
    public PageResultVO<MemberDO> queryByNameAndMobile(String memberName,
                                                       String mobile,
                                                       PageResultVO page) {

        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());


        List<MemberDO> memberDOList = this.memberDOMapper.queryByNameAndMobile(memberName,mobile);


        PageResultVO<MemberDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(memberDOList);

        return result;


    }

    /**
     * 分批批量插入
     * @param memberDOS 成员列表
     * @param size 大小
     */
    @Override
    public void insertBatchPartially(List<MemberDO> memberDOS, int size) {
        List<List<MemberDO>> memberDOSList = CommonUtils.split(memberDOS, size);
        memberDOSList.forEach(this::insertBatch);
    }

    /**
     * 分批批量更新
     * @param memberDOS 成员列表
     * @param size 大小
     */
    @Override
    public void updateBatchByIdPartially(List<MemberDO> memberDOS, int size) {
        List<List<MemberDO>> memberDOSList = CommonUtils.split(memberDOS, size);
        memberDOSList.forEach(this::updateBatchById);
    }

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    @Override
    public int updateInactiveStatus(String tenantId, String taskId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("将记录状态置为失效, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("将记录状态置为失效, taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, taskId不能为空");
        }
        return memberDOMapper.updateInactiveStatus(tenantId, taskId);
    }

    @Override
    public MemberDO queryMemberDetailByUserId(String userId) {

        MemberDO memberDO  = this.memberDOMapper.queryByUserId(userId);

        return memberDO;
    }

    /**
     * 查询成员用户名
     * @param tenantId 租户id
     * @param userIdS 成员userids
     * @return 成员用户名及userid
     */
    @Override
    public List<MemberDO> queryMemberNameByTenantIdAndUserIdS(String tenantId, List<String> userIdS) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id和用户id查询记录, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, tenantId不能为空");
        }
        if (CollectionUtils.isEmpty(userIdS)) {
            LOGGER.error("通过机构id和用户id查询记录, userIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, userIdS不能为空");
        }
        return memberDOMapper.queryMemberNameByTenantIdAndUserIdS(tenantId, userIdS);
    }
}
