package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.biz.service.TagsService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.TagsDO;
import cn.com.xinxin.sass.web.convert.CustomerConvert;
import cn.com.xinxin.sass.web.form.WeChatCustomerQueryForm;
import cn.com.xinxin.sass.web.vo.CustomerVO;
import cn.com.xinxin.sass.web.vo.MemberDetailVO;
import cn.com.xinxin.sass.web.vo.TagsVO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


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

    private final TagsService tagsService;

    private final MemberService memberService;

    public WeChatOrgCustomerRestController(final CustomerService customerService,
                                           final TagsService tagsService,
                                           final MemberService memberService) {
        this.customerService = customerService;
        this.tagsService = tagsService;
        this.memberService = memberService;
    }



    /**
     * 查询客户信息
     * @param request http请求
     * @param queryForm 请求参数
     * @return 客户信息
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Object listWechatCustomers(HttpServletRequest request,
                                                     @RequestBody WeChatCustomerQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信客户信息，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，参数不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //查询客户信息
        PageResultVO<CustomerDO> pageResultDO = customerService.queryCustomerByPages(page);

        List<String> customerIds = pageResultDO.getItems().stream()
                .map(x->x.getUserId())
                .distinct()
                .collect(toList());

        Map<String,List<TagsDO>> tagsDOSMaps = this.tagsService.selectTagsMapsByKeyIdLists(customerIds);


        List<CustomerVO> customerVOS = CustomerConvert.convert2CustomerVOList(pageResultDO.getItems());

        for(CustomerVO customerVO: customerVOS){
            List<TagsDO> customerTagDOList = tagsDOSMaps.get(customerVO.getUserId());
            List<TagsVO> tagsVOList = BaseConvert.convertList(customerTagDOList,TagsVO.class);
            customerVO.setTags(tagsVOList);
        }

        //将DO装换为VO
        PageResultVO<CustomerVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(customerVOS);
        return pageResultVO;
    }



    /**
     * 查询客户信息
     * @param request http请求
     * @param queryForm 请求参数
     * @return 客户信息
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public Object listByOrgIdAndMemberUserIdSAndTime(HttpServletRequest request,
                                            @RequestBody WeChatCustomerQueryForm queryForm){

        //参数检查
        if (null == queryForm) {
            LOGGER.error("查询企业微信客户信息，参数不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，参数不能为空");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String tenantId = queryForm.getTenantId();

        if (StringUtils.isBlank(queryForm.getTenantId())) {

            tenantId = sassUserInfo.getTenantId();
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //将时间戳格式转化为string
        String startTime = StringUtils.isBlank(queryForm.getStartTime()) ? ""
                : DateUtils.formatTime(new Long(queryForm.getStartTime()), DateUtils.DATE_FORMAT_WHIPP_TIME);
        String endTime = StringUtils.isBlank(queryForm.getEndTime()) ? ""
                : DateUtils.formatTime(new Long(queryForm.getEndTime()), DateUtils.DATE_FORMAT_WHIPP_TIME);

        //查询客户信息
        PageResultVO<CustomerDO> pageResultDO = customerService.queryByOrgIdAndMemberUserIdSAndTime(
                queryForm.getMemberUserIds(), startTime, endTime, page, tenantId, queryForm.getCustomerName());
        
        List<String> customerIds = pageResultDO.getItems().stream()
                .map(x->x.getUserId())
                .distinct()
                .collect(toList());

        Map<String,List<TagsDO>> tagsDOSMaps = this.tagsService.selectTagsMapsByKeyIdLists(customerIds);


        List<CustomerVO> customerVOS = CustomerConvert.convert2CustomerVOList(pageResultDO.getItems());

        for(CustomerVO customerVO: customerVOS){
            List<TagsDO> customerTagDOList = tagsDOSMaps.get(customerVO.getUserId());
            List<TagsVO> tagsVOList = BaseConvert.convertList(customerTagDOList,TagsVO.class);
            customerVO.setTags(tagsVOList);
        }

        //将DO装换为VO
        PageResultVO<CustomerVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(customerVOS);

        return pageResultVO;
    }

    /**
     * 根据id查询客户详情
     * @param request 请求
     * @param id id
     * @return 客户详情
     */
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object queryWeChatCustomerDetail(HttpServletRequest request,
                                          @PathVariable Long id) {
        if (null == id) {
            LOGGER.error("查询企业微信客户信息，id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户信息，id不能为空");
        }

        CustomerDO customerDO =  customerService.queryById(id);

        CustomerVO customerVO = CustomerConvert.convert2CustomerVO(customerDO);

        String userId = customerVO.getMemberUserId();

        MemberDO memberDO = this.memberService.queryMemberDetailByUserId(userId);

        customerVO.setMemberName(memberDO.getMemberName());

        // 查询客户的标签
        List<TagsDO> tagsDOList = this.tagsService.selectTagsByKeyId(customerDO.getUserId());

        List<TagsVO> tagsVOList = BaseConvert.convertList(tagsDOList,TagsVO.class);

        customerVO.setTags(tagsVOList);

        return customerVO;
    }


}
