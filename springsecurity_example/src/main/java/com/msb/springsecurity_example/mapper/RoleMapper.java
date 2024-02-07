package com.msb.springsecurity_example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.springsecurity_example.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户ID获取用户拥有的角色信息
     * @param id
     * @return
     */
    List<String> selectRolesByUserId(Long id);
}
