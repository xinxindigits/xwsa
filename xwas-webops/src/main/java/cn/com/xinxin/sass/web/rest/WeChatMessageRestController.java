package cn.com.xinxin.sass.web.rest;

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

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.model.bo.ChatPartyBO;
import cn.com.xinxin.sass.biz.service.GroupChatService;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.biz.vo.PageVO;
import cn.com.xinxin.sass.biz.vo.QueryMsgConditionVO;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.constants.WeChatWorkChatRecordsTypeConstants;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import cn.com.xinxin.sass.web.convert.ChatPartyConvert;
import cn.com.xinxin.sass.web.convert.MessageConvert;
import cn.com.xinxin.sass.web.form.WeChatMessageQueryForm;
import cn.com.xinxin.sass.web.vo.MsgRecordVO;
import com.alibaba.fastjson.JSONObject;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信部门相关的功能管理接口
 */
@RestController
@RequestMapping(value = "/wechat/message",produces = "application/json; charset=UTF-8")
public class WeChatMessageRestController extends AclController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatMessageRestController.class);

    private final MsgRecordService msgRecordService;
    private final GroupChatService groupChatService;

    public WeChatMessageRestController(final MsgRecordService msgRecordService,
                                       final GroupChatService groupChatService) {
        this.msgRecordService = msgRecordService;
        this.groupChatService = groupChatService;
    }

    /**
     * 查询会话消息记录
     * @param request http请求
     * @param queryForm 请求参数
     * @return 会话消息记录
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object listByOrgIdAndMemberUserIdSAndTime(HttpServletRequest request,
                                                     @RequestBody WeChatMessageQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信会话记录，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //查询客户信息
        PageResultVO<MsgRecordDO> pageResultDO = msgRecordService.queryByOrgIdAndMemberUserIdAndTime(
                queryForm.getUserId(), queryForm.getStartTime(), queryForm.getEndTime(), page, sassUserInfo.getTenantId(),
                queryForm.getKeyWord());

        //将DO装换为VO
        PageResultVO<MsgRecordVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        List<MsgRecordVO> msgRecordVOS = new ArrayList<>();
        pageResultDO.getItems().forEach(m -> {
            try {
                msgRecordVOS.add(getMsgRecordVO(m, sassUserInfo.getTenantId()));
            } catch (Exception e) {
                LOGGER.info("获取聊天记录异常", e);
            }
        });
        pageResultVO.setItems(msgRecordVOS);

        return pageResultVO;
    }

    /**
     * 根据id查询会话消息详情
     * @param request 请求
     * @param id id
     * @return 会话消息详情
     */
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryWeChatMsgRecordDetail(HttpServletRequest request,
                                            @PathVariable Long id) {

        SassUserInfo sassUserInfo = this.getSassUser(request);

        if (null == id) {
            LOGGER.error("查询企业微信客户信息，id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，id不能为空");
        }

        MsgRecordDO msgRecordDO = msgRecordService.queryById(id);

        if (null == msgRecordDO || !sassUserInfo.getTenantId().equals(msgRecordDO.getTenantId())) {
            LOGGER.error("查询企业微信客户信息, 数据不存在");
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "查询数据不存在");
        }
        return getMsgRecordVO(msgRecordDO, sassUserInfo.getTenantId());
    }

    @RequestMapping(value = "/query/user",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryMsgRecordBetweenUsers(HttpServletRequest request, @RequestBody WeChatMessageQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信会话记录，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        if (StringUtils.isBlank(queryForm.getUserId())||StringUtils.isBlank(queryForm.getUserIdTwo())) {
            LOGGER.error("查询企业微信会话记录，用户id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，用户id不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        QueryMsgConditionVO conditionVO  = BaseConvert.convert(queryForm, QueryMsgConditionVO.class);
        //查询客户信息
        PageResultVO<MsgRecordDO> pageResultDO = msgRecordService.selectMsgRecordBetweenPersons(page,queryForm.getTenantId(),
                queryForm.getUserId(),queryForm.getUserIdTwo(),conditionVO);
        ChatUserVO chatUserOneVO = msgRecordService.getChatUser(queryForm.getTenantId(),queryForm.getUserId());
        ChatUserVO chatUserTwoVO = msgRecordService.getChatUser(queryForm.getTenantId(),queryForm.getUserIdTwo());
        //将DO装换为VO
        PageResultVO<MsgRecordVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(MessageConvert.convert2MsgRecordVOList(pageResultDO.getItems(),chatUserOneVO,chatUserTwoVO));

        return pageResultVO;
    }

    @RequestMapping(value = "/query/room",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryRoomMsgRecord(HttpServletRequest request, @RequestBody WeChatMessageQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信会话记录，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        if (StringUtils.isBlank(queryForm.getRoomId())) {
            LOGGER.error("查询企业微信会话记录，群聊id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，群聊id不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        QueryMsgConditionVO conditionVO  = BaseConvert.convert(queryForm, QueryMsgConditionVO.class);

        //查询客户信息
        PageResultVO<MsgRecordDO> pageResultDO = msgRecordService.selectRoomMsgRecord(page,queryForm.getTenantId(),
                queryForm.getRoomId(),conditionVO);

        //将DO装换为VO
        PageResultVO<MsgRecordVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());

        if(!CollectionUtils.isEmpty(pageResultDO.getItems())){
            List<MsgRecordVO> msgRecordVOS = new ArrayList<>(pageResultDO.getItems().size());
            Map<String,ChatUserVO> chatUserMap = new HashMap<>();
            pageResultDO.getItems().stream().forEach(msgRecordDO -> {
                if(!chatUserMap.containsKey(msgRecordDO.getFromUserId())){
                    ChatUserVO chatUserVO = msgRecordService.getChatUser(msgRecordDO.getTenantId(),msgRecordDO.getFromUserId());
                    chatUserMap.put(chatUserVO.getChatUserId(),chatUserVO);
                }
                MsgRecordVO msgRecordVO = BaseConvert.convert(msgRecordDO, MsgRecordVO.class);
                if(WeChatWorkChatRecordsTypeConstants.TEXT.equals(msgRecordDO.getMsgType())){
                    msgRecordVO.setContent((String)JSONObject.parseObject(msgRecordDO.getContent()).get(CommonConstants.CONTENT));
                }
                msgRecordVO.setFromUserName(chatUserMap.get(msgRecordDO.getFromUserId()).getChatUserName());
                msgRecordVO.setAvatar(chatUserMap.get(msgRecordDO.getFromUserId()).getAvatar());
                msgRecordVOS.add(msgRecordVO);
            });
            pageResultVO.setItems(msgRecordVOS);
        }

        return pageResultVO;
    }

    /**
     * 根据成员userid查询与之聊天的人或者群
     * @param request http请求
     * @param queryForm 查询表单
     * @return 聊天的人或者群
     */
    @RequestMapping(value = "/query/userList",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryChatPartyList(HttpServletRequest request, @RequestBody WeChatMessageQueryForm queryForm) {
        if (null == queryForm) {
            LOGGER.error("根据成员userid查询与之聊天的人或者群, queryForm不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据成员userid查询与之聊天的人或者群, queryForm不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        if (StringUtils.isBlank(queryForm.getUserId())) {
            LOGGER.error("根据成员userid查询与之聊天的人或者群, UserId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据成员userid查询与之聊天的人或者群, UserId不能为空");
        }

        List<ChatPartyBO> chatPartyBOS = msgRecordService.selectByMemberUserIdAndKeyWordAndTime(
                sassUserInfo.getTenantId(), queryForm.getUserId(), queryForm.getKeyWord(), queryForm.getStartTime(),
                queryForm.getEndTime());

        chatPartyBOS.stream()
                .filter(c -> 1 == c.getType())
                .forEach(c -> {
                    String chatName = groupChatService.queryChatNameByChatId(c.getRoomId(), sassUserInfo.getTenantId());
                    c.setRoomName(chatName);
                    if (StringUtils.equals(chatName, c.getRoomId())) {
                        c.setRoomName(getToUserName(c.getToUserList(), sassUserInfo.getTenantId(), c.getRoomId()));
                    }
                });

        chatPartyBOS.stream().filter(c -> 0 == c.getType()).forEach(c -> {
            ChatUserVO chatUserVO = msgRecordService.getChatUser(sassUserInfo.getTenantId(), c.getUserId());
            c.setUserName(chatUserVO.getChatUserName());
            c.setAvatar(chatUserVO.getAvatar());
        });

        return ChatPartyConvert.convert2ChatPartyList(chatPartyBOS);
    }

    /**
     * 查询会话记录所在页码
     * @param queryForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryPageIndex")
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryPageIndex(@RequestBody WeChatMessageQueryForm queryForm, HttpServletRequest request){
        if (null == queryForm) {
            LOGGER.error("查询会话记录所在页码, queryForm不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    " 查询会话记录所在页码,queryForm不能为空");
        }
        if (queryForm.getId() == null) {
            LOGGER.error("查询会话记录所在页码, 消息id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询会话记录所在页码, 消息id不能为空");
        }
        if (StringUtils.isEmpty(queryForm.getRoomId()) && (StringUtils.isEmpty(queryForm.getUserId()) || StringUtils.isEmpty(queryForm.getUserIdTwo()))) {
            LOGGER.error("查询会话记录所在页码, 群聊id和用户id不能同时为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询会话记录所在页码, 群聊id和用户id不能同时为空");
        }
        String tenantId = "";
        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(StringUtils.isEmpty(queryForm.getTenantId())){
            tenantId = sassUserInfo.getTenantId();
        }else{
            tenantId = queryForm.getTenantId();
        }
        Integer pageSize = (queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize();
        PageVO result = msgRecordService.getPageIndex(queryForm.getId(),tenantId,queryForm.getRoomId(),
                queryForm.getUserId(),queryForm.getUserIdTwo(),pageSize);
        return result;
    }

    /**
     * 获取ToUserName
     * @param toUserId 接受方id
     * @param tenantId 租户id
     * @param roomId 群id
     * @return 接受方名
     */
    private String getToUserName(String toUserId, String tenantId, String roomId) {
        StringBuilder sb = new StringBuilder();
        String[] userIdS = toUserId.substring(1, toUserId.length() - 1).split(", ");
        List<String> userIdList = new ArrayList<>(userIdS.length);
        Collections.addAll(userIdList, userIdS);
        msgRecordService.getChatPartyNameList(tenantId, userIdList).forEach(s -> sb.append(s).append(","));
        if (StringUtils.isBlank(sb)) {
            return roomId;
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    /**
     * 讲DO装化为BO
     * @param msgRecordDO 记录
     * @param tenantId 租户id
     * @return MsgRecordVO
     */
    private MsgRecordVO getMsgRecordVO(MsgRecordDO msgRecordDO, String tenantId) {
        ChatUserVO chatUserVO = msgRecordService.getChatUser(tenantId, msgRecordDO.getFromUserId());
        String toChatPartyName;
        Integer type;
        if (StringUtils.isBlank(msgRecordDO.getRoomId())) {
            toChatPartyName = msgRecordService.getChatUser(tenantId,
                    msgRecordDO.getToUserId().substring(1, msgRecordDO.getToUserId().length() - 1))
                    .getChatUserName();
            type = 0;
        } else {
            toChatPartyName = groupChatService.queryChatNameByChatId(msgRecordDO.getRoomId(), tenantId);
            if (StringUtils.equals(toChatPartyName, msgRecordDO.getRoomId())) {
                toChatPartyName = getToUserName(msgRecordDO.getToUserId(), tenantId, msgRecordDO.getRoomId());
            }
            type = 1;
        }
        return MessageConvert.convert2MsgRecordVO(msgRecordDO, chatUserVO.getChatUserName(), toChatPartyName, type);
    }
}
