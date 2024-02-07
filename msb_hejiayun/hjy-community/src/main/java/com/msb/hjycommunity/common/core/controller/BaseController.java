package com.msb.hjycommunity.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msb.hjycommunity.common.constant.HttpStatus;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageDomain;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.ServletUtils;

import java.util.List;

public class BaseController {
    // 当前记录起始索引
    public static final String PAGE_NUM = "pageNum";
    // 每页显示记录数
    public static final String PAGE_SIZE = "pageSize";

    // 封装分页数据
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        return pageDomain;
    }

    /**
     * 只给子类用，封装调用PageHelper的startPage方法
     */
    protected void startPage() {
        PageDomain pageDomain = new PageDomain();
        // 分页参数
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 只给子类用，响应分页数据
     */
    protected PageResult getData(List<?> list) {
        // 封装数据
        PageResult pageResult = new PageResult();
        pageResult.setCode(HttpStatus.SUCCESS);
        pageResult.setMsg("分页查询成功");
        pageResult.setRows(list);
        pageResult.setTotal(new PageInfo<>(list).getTotal());
        return pageResult;
    }

    /**
     * 响应返回结果（针对增删改操作）
     * @param rows 受影响的行数
     * @return
     */
    protected BaseResponse toAjax(int rows) {
        return rows > 0 ? BaseResponse.success(rows) : BaseResponse.fail("操作失败");
    }
}
