package cn.com.xinxin.sass.biz.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.Date;

/**
 * @author: yanghaoxian
 * @created: 2020/5/8.
 * @updater:
 * @description:
 */
public class QueryMsgConditionVO extends ToString {

    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 关键词
     */
    private String keyWord;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
