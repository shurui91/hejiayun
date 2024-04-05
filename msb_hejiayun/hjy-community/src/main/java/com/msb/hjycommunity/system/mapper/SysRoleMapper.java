package com.msb.hjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.hjycommunity.system.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色表数据层
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户id查询角色权限
     * @param userId
     * @return
     */
    public List<String> selectRolePermissionByUserId(Long userId);
}
