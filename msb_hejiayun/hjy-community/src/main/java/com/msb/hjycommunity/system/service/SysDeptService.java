package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.system.domain.SysDept;

import java.util.List;

public interface SysDeptService {
    /**
     * 查询部门管理数据
     * @param sysDept
     * @return
     */
    public List<SysDept> selectDeptList(SysDept sysDept);
}
