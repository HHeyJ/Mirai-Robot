package com.hyq.robot.query;

/**
 * @author nanke
 * @date 2019/10/6 000612:20
 */
public class BaseQuery {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer offset;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        calculateOffset();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        calculateOffset();
    }

    public Integer getOffset() {
        calculateOffset();
        return offset;
    }

    private void calculateOffset() {
        if (null == pageNo || null == pageSize) {
            return;
        }
        offset = (pageNo - 1) * pageSize;
    }
}
