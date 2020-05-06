package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.TagsService;
import cn.com.xinxin.sass.repository.dao.TagsDOMapper;
import cn.com.xinxin.sass.repository.dao.TagsRelationsDOMapper;
import cn.com.xinxin.sass.repository.model.TagsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhouyang
 * @created: 06/05/2020.
 * @updater:
 * @description:
 */
@Service
public class TagsServiceImpl implements TagsService {


    @Autowired
    private TagsDOMapper tagsDOMapper;

    @Autowired
    private TagsRelationsDOMapper tagsRelationsDOMapper;



    @Override
    public int createTags(TagsDO tagsDO) {

        return tagsDOMapper.insertSelective(tagsDO);

    }
}
