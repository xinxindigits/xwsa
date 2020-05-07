package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.TagsService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.TagsDOMapper;
import cn.com.xinxin.sass.repository.dao.TagsRelationsDOMapper;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.TagsDO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: zhouyang
 * @created: 06/05/2020.
 * @updater:
 * @description:
 */
@Service
public class TagsServiceImpl implements TagsService {



    private static final Logger logger = LoggerFactory.getLogger(TagsServiceImpl.class);

    @Autowired
    private TagsDOMapper tagsDOMapper;

    @Autowired
    private TagsRelationsDOMapper tagsRelationsDOMapper;



    @Override
    public int createTags(TagsDO tagsDO) {

        return tagsDOMapper.insertSelective(tagsDO);

    }

    @Override
    public PageResultVO<TagsDO> queryTagsByPages(PageResultVO page, String tagName) {

        logger.info("queryTagsByPages:{}", tagName);
        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());


        List<TagsDO> tagsDOList = this.tagsDOMapper.selectByTagName(tagName);

        PageResultVO<TagsDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setItems(tagsDOList);
        result.setTotal(doPage.getTotal());

        return result;

    }

    @Override
    public int updateTags(TagsDO tagsDO) {

        return tagsDOMapper.updateByPrimaryKeySelective(tagsDO);

    }

    @Override
    public int deleteTagsByIds(Long tagsId) {

       return tagsDOMapper.deleteByPrimaryKey(tagsId);

    }
}
