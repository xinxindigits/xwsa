package cn.com.xinxin.sass.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengyunhui on 2018/5/4
 **/
public class TreeVO{

    /**
     * 节点id
     */
    private String id;

    /**
     * 显示节点文本
     */
    private String text;

    /**
     * 节点的子节点
     */
    private List<TreeVO> children = new ArrayList<>();

    private List<TreeVO> items = new ArrayList<>();

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 是否默认展开
     */
    private Boolean expanded;

    /**
     * 展示时，节点是否选中
     */
    private Boolean checked;

    private String url;

    private String href;

    private String code;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<TreeVO> getItems() {
        return items;
    }

    public void setItems(List<TreeVO> items) {
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
