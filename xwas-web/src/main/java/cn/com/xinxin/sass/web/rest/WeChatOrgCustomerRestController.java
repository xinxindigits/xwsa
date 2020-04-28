package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.web.convert.CustomerConvert;
import cn.com.xinxin.sass.web.form.WeChatCustomerQueryForm;
import cn.com.xinxin.sass.web.vo.CustomerVO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: liuhangzhou
 * @created: 2020/4/28.
 * @updater:
 * @description: 企业微信客户信息api
 */
@RestController
@RequestMapping(value = "/wechat/customer",produces = "application/json; charset=UTF-8")
public class WeChatOrgCustomerRestController extends AclController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatOrgCustomerRestController.class);

    private final CustomerService customerService;

    public WeChatOrgCustomerRestController(final CustomerService customerService) {
        this.customerService = customerService;
    }


    /**
     * 查询客户信息
     * @param request http请求
     * @param queryForm 请求参数
     * @return 客户信息
     */
    @PostMapping(value = "/query")
    @ResponseBody
    public Object listByOrgIdAndMemberUserIdSAndTime(HttpServletRequest request,
                                            @RequestBody WeChatCustomerQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信客户信息，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，参数不能为空");
        }
        if (StringUtils.isBlank(queryForm.getOrgId())) {
            LOGGER.error("查询企业微信客户信息，机构id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，机构id不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageNum() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageNum());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //查询客户信息
        PageResultVO<CustomerDO> pageResultDO = customerService.queryByOrgIdAndMemberUserIdSAndTime(
                queryForm.getMemberUserIds(), queryForm.getStartTime(), queryForm.getEndTime(), page, queryForm.getOrgId());

        //将DO装换为VO
        PageResultVO<CustomerVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(CustomerConvert.convert2CustomerVOList(pageResultDO.getItems()));

        return pageResultVO;
    }



}
