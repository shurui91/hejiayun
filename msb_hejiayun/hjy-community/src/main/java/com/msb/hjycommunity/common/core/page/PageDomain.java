package com.msb.hjycommunity.common.core.page;

/**
 * 分页参数封装
 * */
public class PageDomain {

    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
