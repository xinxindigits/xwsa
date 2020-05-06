package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.TagsDO;

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



}
