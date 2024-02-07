package com.msb.springsecurity_example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.springsecurity_example.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    // 根据用户id获取用户权限信息
    List<String> selectPermsByUserId(Long id);
}
