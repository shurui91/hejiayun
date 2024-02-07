package com.msb.hjycommunity.system.mapper;

import com.msb.hjycommunity.system.domain.SysArea;

import java.util.List;

/**
 * 注意与HjycommunityMapper实现方式的不同
 */
public interface SysAreaMapper {
    List<SysArea> findAll();
}
