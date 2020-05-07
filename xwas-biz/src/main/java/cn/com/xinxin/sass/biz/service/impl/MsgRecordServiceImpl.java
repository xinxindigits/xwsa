package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.model.bo.ChatPartyBO;
import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.common.enums.ChatUserEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.MsgRecordDOMapper;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import com.github.pagehelper.PageHelper;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    private final MemberService memberService;

    private final CustomerService customerService;

    public MsgRecordServiceImpl(MsgRecordDOMapper msgRecordDOMapper, MemberService memberService, CustomerService customerService) {
        this.msgRecordDOMapper = msgRecordDOMapper;
        this.memberService = memberService;
        this.customerService = customerService;
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

    @Override
    public PageResultVO<MsgRecordDO> selectMsgRecordBetweenPersons(PageResultVO page,String orgId, String userIdOne, String userIdTwo) {
        if (StringUtils.isBlank(orgId)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "机构编码不能为空");
        }
        if (StringUtils.isBlank(userIdOne) || StringUtils.isBlank(userIdTwo)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "用户id不能为空");
        }
        com.github.pagehelper.Page pageHelper = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<MsgRecordDO> resourceDOS = msgRecordDOMapper.selectMsgRecordBetweenPersons(orgId,userIdOne,userIdTwo);
        PageResultVO<MsgRecordDO> result = new PageResultVO<>();
        result.setTotal(pageHelper.getTotal());
        result.setItems(resourceDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());
        return result;
    }

    @Override
    public PageResultVO<MsgRecordDO> selectRoomMsgRecord(PageResultVO page, String orgId, String roomId) {
        if (StringUtils.isBlank(orgId)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "机构编码不能为空");
        }
        if (StringUtils.isBlank(roomId)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "群聊id不能为空");
        }
        com.github.pagehelper.Page pageHelper = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<MsgRecordDO> resourceDOS = msgRecordDOMapper.selectRoomMsgRecord(orgId,roomId);
        PageResultVO<MsgRecordDO> result = new PageResultVO<>();
        result.setTotal(pageHelper.getTotal());
        result.setItems(resourceDOS);
        result.setPageSize(page.getPageSize());
        result.setPageNumber(page.getPageNumber());
        return result;
    }

    @Override
    public ChatUserVO getChatUser(String orgId, String chatUserId) {
        if (StringUtils.isBlank(orgId)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "机构编码不能为空");
        }
        if (StringUtils.isBlank(chatUserId)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "用户id不能为空");
        }
        ChatUserVO result = new ChatUserVO();
        result.setChatUserId(chatUserId);
        List<MemberDO> member = memberService.queryByOrgIdAndUserId(orgId,Arrays.asList(chatUserId));
        if(!CollectionUtils.isEmpty(member)){
            result.setChatUserName(member.get(0).getMemberName());
            result.setChatUserType(ChatUserEnum.MEMBER.getCode());
        }else{
            List<CustomerDO> customer = customerService.selectByOrgIdAndUserId(orgId,Arrays.asList(chatUserId));
            if(!CollectionUtils.isEmpty(customer)){
                result.setChatUserName(customer.get(0).getCustomerName());
                result.setChatUserType(ChatUserEnum.CUSTOMER.getCode());
            }else{
                result.setChatUserName(chatUserId);
                result.setChatUserType(ChatUserEnum.OTHER.getCode());
            }
        }
        return result;
    }

    /**
     * 通过租户id和成员userid查询聊天方
     * @param tenantId 租户id
     * @param userId 成员userid
     * @return 聊天方
     */
    @Override
    public List<ChatPartyBO> selectByMemberUserId(String tenantId, String userId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过租户id和成员userid查询会话记录, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过租户id和成员userid查询会话记录, tenantId不能为空");
        }
        if (StringUtils.isBlank(userId)) {
            LOGGER.error("通过租户id和成员userid查询会话记录, userId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过租户id和成员userid查询会话记录, userId不能为空");
        }
        List<MsgRecordDO> msgRecordDOS = msgRecordDOMapper.selectByMemberUserId(tenantId, userId);

        Map<String, ChatPartyBO> chatPartyPersonBOMap = new HashMap<>();
        Map<String, ChatPartyBO> chatPartyRoomBOMap = new HashMap<>();

        //组装聊天方信息
        msgRecordDOS.forEach(r -> {
            if (!StringUtils.equals(r.getFromUserId(), userId) && !chatPartyPersonBOMap.containsKey(r.getFromUserId())) {
                ChatPartyBO chatPartyBO = new ChatPartyBO();
                chatPartyBO.setType(0);
                chatPartyBO.setUserId(r.getFromUserId());
                chatPartyPersonBOMap.put(r.getFromUserId(), chatPartyBO);
            }


            if (StringUtils.isBlank(r.getRoomId())) {
                String toUserId = r.getToUserId().replace("[", "").replace("]", "");
                if (!chatPartyPersonBOMap.containsKey(toUserId) && !StringUtils.equals(toUserId, userId)) {
                    ChatPartyBO chatPartyBO = new ChatPartyBO();
                    chatPartyBO.setType(0);
                    chatPartyBO.setUserId(toUserId);
                    chatPartyPersonBOMap.put(toUserId, chatPartyBO);
                }
            } else {
                if (!chatPartyRoomBOMap.containsKey(r.getRoomId())) {
                    ChatPartyBO chatPartyBO = new ChatPartyBO();
                    chatPartyBO.setType(1);
                    chatPartyBO.setRoomId(r.getRoomId());
                    //Fixme 暂时写死
                    chatPartyBO.setRoomName("群聊" + chatPartyRoomBOMap.size());
                    chatPartyRoomBOMap.put(r.getRoomId(), chatPartyBO);
                }
            }
        });

        List<ChatPartyBO> chatPartyBOS = new ArrayList<>(chatPartyPersonBOMap.values());
        chatPartyBOS.addAll(chatPartyRoomBOMap.values());

        return chatPartyBOS;
    }
}
