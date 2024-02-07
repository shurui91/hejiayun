package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.system.domain.SysDept;
import com.msb.hjycommunity.system.mapper.SysDeptMapper;
import com.msb.hjycommunity.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     * @param sysDept
     * @return
     */
    @Override
    public List<SysDept> selectDeptList(SysDept sysDept) {
        return deptMapper.selectDeptList(sysDept);
    }
}
