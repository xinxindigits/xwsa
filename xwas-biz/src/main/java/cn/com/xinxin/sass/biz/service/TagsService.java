package cn.com.xinxin.sass.biz.service;

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

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TagsDO;
import cn.com.xinxin.sass.repository.model.TagsRelationsDO;


import java.util.List;
import java.util.Map;

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
     * 删除
     * @param keyId
     * @return
     */
    int deleteTagsBykeyId(String keyId);


    /**
     * 创建tag对应的映射关系
     * @param tagsRelationsDOS
     * @return
     */
    int createTagsRelations(List<TagsRelationsDO> tagsRelationsDOS);


    /**
     * 查询某个key对应的tagList
     * @param keyId
     * @return
     */
    List<TagsDO> selectTagsByKeyId(String keyId);


    /**
     * 查询某些keyID对应的礼拜
     * @param keyIds
     * @return
     */
    List<TagsDO> selectTagsByKeyIdLists(List<String> keyIds);


    /**
     *
     * @param keyIds
     * @return
     */
    Map<String,List<TagsDO>> selectTagsMapsByKeyIdLists(List<String> keyIds);


    /**
     * all tags
     * @param tagName
     * @param tenantId
     * @return
     */
    List<TagsDO> queryAllTagsByNameAndTenantId(String tagName,String tenantId);

}
