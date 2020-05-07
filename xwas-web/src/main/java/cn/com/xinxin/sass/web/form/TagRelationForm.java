package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 06/05/2020.
 * @updater:
 * @description:
 */
public class TagRelationForm extends ToString {

    private static final long serialVersionUID = -6737547660318839566L;

    private Long id;

    private List<String> tagIds;

    private String keyId;

    private String keyName;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
