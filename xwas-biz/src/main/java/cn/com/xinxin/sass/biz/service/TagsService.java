package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TagsDO;
import cn.com.xinxin.sass.repository.model.TagsRelationsDO;


import java.util.List;

/**
 * @author: zhouyang
 * @created: 06/05/2020.
 * @updater:
 * @description:
 */
public interface TagsService {


    /**
     * 创建标签
     * @param tagsDO
     * @return
     */
    int createTags(TagsDO tagsDO);


    /**
     * 查询条件
     * @param page
     * @return
     */
    PageResultVO<TagsDO> queryTagsByPages(PageResultVO page,
                                          String tagName);


    /**
     * update动作
     * @param tagsDO
     * @return
     */
    int updateTags(TagsDO tagsDO);


    /**
     * 删除动作
     * @param tagsIds
     * @return
     */
    int deleteTagsByIds(Long tagsIds);


    /**
     * 创建tag对应的映射关系
     * @param tagsRelationsDOS
     * @return
     */
    int createTagsRelations(List<TagsRelationsDO> tagsRelationsDOS);



}
