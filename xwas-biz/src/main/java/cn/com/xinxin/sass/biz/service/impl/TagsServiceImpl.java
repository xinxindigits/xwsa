package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.TagsService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.TagsDOMapper;
import cn.com.xinxin.sass.repository.dao.TagsRelationsDOMapper;
import cn.com.xinxin.sass.repository.model.TagsDO;
import cn.com.xinxin.sass.repository.model.TagsMapDO;
import cn.com.xinxin.sass.repository.model.TagsRelationsDO;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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


        // 首先删除tags然后删除映射关系
        int result = this.tagsDOMapper.deleteByPrimaryKey(tagsId);

        this.tagsRelationsDOMapper.deleteTagsRelationsByTagId(tagsId);

        return result;


    }

    @Override
    public int createTagsRelations(List<TagsRelationsDO> tagsRelationsDOS) {

        this.tagsRelationsDOMapper.batchCreateRelations(tagsRelationsDOS);

        return 1;
    }

    @Override
    public List<TagsDO> selectTagsByKeyId(String keyId) {



        List<TagsDO> tagsDOList = this.tagsRelationsDOMapper.selectTagsByKeyId(keyId);

        return tagsDOList;
    }

    @Override
    public List<TagsDO> selectTagsByKeyIdLists(List<String> keyIds) {

        if(CollectionUtils.isEmpty(keyIds)){

            return Lists.newArrayList();
        }

        List<TagsDO> tagsDOList = this.tagsRelationsDOMapper.selectTagsByKeyIdLists(keyIds);

        return tagsDOList;
    }

    @Override
    public Map<String, List<TagsDO>> selectTagsMapsByKeyIdLists(List<String> keyIds) {


        if(CollectionUtils.isEmpty(keyIds)){

            return Maps.newHashMap();
        }

        List<TagsMapDO> tagsMapDOList = this.tagsRelationsDOMapper.selectTagsMapsByKeyIds(keyIds);

        Map<String, List<TagsDO>> resultMaps = Maps.newHashMap();

        for(String keyId: keyIds){

            List<TagsMapDO> mapDOList = tagsMapDOList.stream()
                    .filter(x->StringUtils.equals(keyId,x.getKeyId()))
                    .collect(Collectors.toList());

            List<TagsDO> tagsDOList = BaseConvert.convertList(mapDOList,TagsDO.class);

            resultMaps.put(keyId,tagsDOList);

        }

        return resultMaps;
    }

    @Override
    public List<TagsDO> queryAllTagsByNameAndTenantId(String tagName, String tenantId) {

       List<TagsDO> tagsDOList =  this.tagsDOMapper.queryAllTagsByNameAndTenantId(tagName,tenantId);

        return tagsDOList;
    }

    @Override
    public int deleteTagsBykeyId(String keyId) {

       return this.tagsRelationsDOMapper.deleteTagsBykeyId(keyId);
    }
}
