package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.web.utils.TreeUtil;
import cn.com.xinxin.sass.web.vo.TreeVO;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengyunhui on 2018/4/26
 **/
@Controller
public class IndexController  extends AclController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/dashboard")
    public String index(HttpServletRequest request,Model model){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        LOGGER.info("IndexController index and user = {} " , sassUserInfo.getAccount());

        List<ResourceDO> resourceDOS = userService.findMenusByAccount(sassUserInfo.getAccount());
        List<TreeVO> trees = new ArrayList<>();
        List<TreeVO> menus = new ArrayList<>();

        if (!CollectionUtils.isEmpty(resourceDOS)){
            for (ResourceDO  resourceDO : resourceDOS) {
                TreeVO treeVO = new TreeVO();
                treeVO.setText(resourceDO.getName());
                treeVO.setParentId(resourceDO.getParentId() + "");
                treeVO.setId(resourceDO.getId() + "");
                treeVO.setCode(resourceDO.getCode());
                treeVO.setUrl(resourceDO.getUrl());
                treeVO.setHref(resourceDO.getUrl());

                trees.add(treeVO);
            }

            menus = TreeUtil.build(trees);
        }

        String jsonObject = JSONObject.toJSONString(menus);

        LOGGER.info("IndexController.index user:{},menus:{}", sassUserInfo.getAccount(),menus);

        model.addAttribute("menus",jsonObject);
        model.addAttribute("username", sassUserInfo.getName());

        return "main";
    }

}
