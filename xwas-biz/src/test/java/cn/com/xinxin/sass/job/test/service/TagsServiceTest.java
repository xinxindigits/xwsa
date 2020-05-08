package cn.com.xinxin.sass.job.test.service;

import cn.com.xinxin.sass.biz.service.TagsService;
import cn.com.xinxin.sass.job.test.base.SpringBaseTest;
import cn.com.xinxin.sass.repository.model.TagsDO;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author: zhouyang
 * @created: 08/05/2020.
 * @updater:
 * @description:
 */
public class TagsServiceTest extends SpringBaseTest {


    @Autowired
    private TagsService tagsService;


    @Test
    public void testTagQueryBykeyIs(){


        List<TagsDO> tagsDOList = this.tagsService.selectTagsByKeyId("1000");

        System.out.println(tagsDOList.size());

        List<TagsDO> tagsDOLists = this.tagsService.selectTagsByKeyIdLists(Lists.newArrayList("1000"));

        System.out.println(tagsDOLists.size());

        Map<String,List<TagsDO>> tagMaps = this.tagsService.selectTagsMapsByKeyIdLists(Lists.newArrayList("1000","woD5WOCgAA9Ser587NLqXW_msZLZj8pQ"));

        System.out.println(tagMaps.size());

    }
}
