package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.MsgRecordDOMapper;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 消息记录服务
 */
@Service
public class MsgRecordServiceImpl implements MsgRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

    private final MsgRecordDOMapper msgRecordDOMapper;

    public MsgRecordServiceImpl(final MsgRecordDOMapper msgRecordDOMapper) {
        this.msgRecordDOMapper = msgRecordDOMapper;
    }

    /**
     * 批量插入记录
     * @param msgRecordDOS 消息记录
     * @return 插入记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int insertBatch(List<MsgRecordDO> msgRecordDOS) {
        if (CollectionUtils.isEmpty(msgRecordDOS)) {
            LOGGER.warn("批量插入消息记录， msgRecordDOS为空");
            return 0;
        }
        return msgRecordDOMapper.insertBatch(msgRecordDOS);
    }

    /**
     * 通过机构id，userid，消息发送时间范围查询消息记录
     * @param userId 用户id
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @param page 分页信息
     * @param orgId 机构id
     * @return 消息记录
     */
    @Override
    public PageResultVO<MsgRecordDO> queryByOrgIdAndMemberUserIdAndTime(String userId, String startTime, String endTime,
                                                                        PageResultVO page, String orgId) {

        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id，userid，消息发送时间范围查询消息记录,orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id，userid，消息发送时间范围查询消息记录,orgId不能为空");
        }
        if (null == page || null == page.getPageNumber() || null == page.getPageSize()) {
            LOGGER.error("通过机构id，userid，消息发送时间范围查询消息记录户,page不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id，userid，消息发送时间范围查询消息记录,page不能为空");
        }

        //计算分页的起始偏移量
        Long index = (page.getPageNumber() - 1) * page.getPageSize().longValue();

        Long count = msgRecordDOMapper.selectCountByOrgIdAndUserIdAndTime(userId, startTime, endTime, orgId);

        List<MsgRecordDO> msgRecordDOS = new ArrayList<>();

        //结果
        PageResultVO<MsgRecordDO> resultVO = new PageResultVO<>();
        resultVO.setPageNumber(page.getPageNumber());
        resultVO.setPageSize(page.getPageSize());
        resultVO.setTotal(count);

        if (count > 0L) {
            //会话记录
            msgRecordDOS = msgRecordDOMapper.selectPageByOrgIdAndUserIdAndTime(userId, startTime,
                    endTime, index, page.getPageSize(), orgId);
        }

        resultVO.setItems(msgRecordDOS);

        return resultVO;
    }

    /**
     * 通过id查询会话详情
     * @param id id
     * @return 消息纪录
     */
    @Override
    public MsgRecordDO queryById(Long id) {
        if (null == id) {
            LOGGER.error("通过id查询会话详情,Id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过id查询会话详情,Id不能为空");
        }
        return msgRecordDOMapper.selectByPrimaryKey(id);
    }
}
