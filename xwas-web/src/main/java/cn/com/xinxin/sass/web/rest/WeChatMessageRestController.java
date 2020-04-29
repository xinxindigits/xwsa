package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import cn.com.xinxin.sass.web.convert.MessageConvert;
import cn.com.xinxin.sass.web.form.WeChatMessageQueryForm;
import cn.com.xinxin.sass.web.vo.MsgRecordVO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    public WeChatMessageRestController(final MsgRecordService msgRecordService) {
        this.msgRecordService = msgRecordService;
    }

    /**
     * 查询会话消息记录
     * @param request http请求
     * @param queryForm 请求参数
     * @return 会话消息记录
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public Object listByOrgIdAndMemberUserIdSAndTime(HttpServletRequest request,
                                                     @RequestBody WeChatMessageQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信会话记录，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，参数不能为空");
        }
        if (StringUtils.isBlank(queryForm.getOrgId())) {
            LOGGER.error("查询企业微信会话记录，机构id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信会话记录，机构id不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageNum() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageNum());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //查询客户信息
        PageResultVO<MsgRecordDO> pageResultDO = msgRecordService.queryByOrgIdAndMemberUserIdAndTime(
                queryForm.getUserId(), queryForm.getStartTime(), queryForm.getEndTime(), page, queryForm.getOrgId());

        //将DO装换为VO
        PageResultVO<MsgRecordVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(MessageConvert.convert2MsgRecordVOList(pageResultDO.getItems()));

        return pageResultVO;
    }

}
