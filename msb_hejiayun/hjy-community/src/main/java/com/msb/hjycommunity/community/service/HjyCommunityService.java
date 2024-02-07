package com.msb.hjycommunity.community.service;

import com.msb.hjycommunity.community.domain.HjyCommunity;
import com.msb.hjycommunity.community.domain.dto.HjyCommunityDto;
import com.msb.hjycommunity.community.domain.vo.HjyCommunityVo;

import java.util.List;

public interface HjyCommunityService {
    /**
     * 根据条件查询小区信息列表
     *
     * @param hjyCommunity
     * @return
     */
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);

    /**
     * 新增小区信息
     *
     * @param hjyCommunity
     * @return int
     */
    int insertHjyCommunity(HjyCommunity hjyCommunity);

    /**
     * 根据ID获取小区详情
     *
     * @param communityId
     * @return
     */
    HjyCommunity selectHjyCommunityById(Long communityId);

    /**
     * 修改小区
     *
     * @param hjyCommunity
     * @return
     */
    int updateHjyCommunity(HjyCommunity hjyCommunity);

    /**
     * 删除小区信息
     *
     * @param hjyCommunity
     * @return
     */
    int deleteHjyCommunity(Long[] communityIds);

    /**
     * 获取小区下拉列表
     * @param hjyCommunity
     * @return
     */
    List<HjyCommunityVo> queryPullDown(HjyCommunity hjyCommunity);
}
