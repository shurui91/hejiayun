package com.msb.hjycommunity.community.service.impl;

import com.msb.hjycommunity.common.utils.OrikaUtils;
import com.msb.hjycommunity.community.domain.HjyCommunity;
import com.msb.hjycommunity.community.domain.dto.HjyCommunityDto;
import com.msb.hjycommunity.community.domain.vo.HjyCommunityVo;
import com.msb.hjycommunity.community.mapper.HjycommunityMapper;
import com.msb.hjycommunity.community.service.HjyCommunityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HjyCommunityServiceImpl implements HjyCommunityService {
    private static final String CODE_PREFIX = "COMMUNITY_";
    @Resource
    private HjycommunityMapper hjycommunityMapper;

    @Override
    public List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity) {
        return hjycommunityMapper.queryList(hjyCommunity);
    }

    @Override
    public int insertHjyCommunity(HjyCommunity hjyCommunity) {
        // 设置小区编码
        hjyCommunity.setCommunityCode(CODE_PREFIX + System.currentTimeMillis());
        return hjycommunityMapper.insert(hjyCommunity);
    }

    @Override
    public HjyCommunity selectHjyCommunityById(Long communityId) {
        return hjycommunityMapper.selectById(communityId);
    }

    /**
     * @param hjyCommunity
     * @return
     */
    @Override
    public int updateHjyCommunity(HjyCommunity hjyCommunity) {
        return hjycommunityMapper.updateById(hjyCommunity);
    }

    /**
     * @param communityIds
     * @return
     */
    @Override
    public int deleteHjyCommunity(Long[] communityIds) {
        return hjycommunityMapper.deleteBatchIds(Arrays.asList(communityIds));
    }

    /**
     * @param hjyCommunity
     * @return
     */
    @Override
    public List<HjyCommunityVo> queryPullDown(HjyCommunity hjyCommunity) {
        List<HjyCommunityDto> dtoList =
                hjycommunityMapper.queryList(hjyCommunity);
        // 对象拷贝
        List<HjyCommunityVo> voList = dtoList.stream().map(dto -> {
            // 使用Orika完成对象拷贝
            HjyCommunityVo hjyCommunityVo = OrikaUtils.convert(dto,
                    HjyCommunityVo.class);
            return hjyCommunityVo;
        }).collect(Collectors.toList());
        return voList;
    }
}
