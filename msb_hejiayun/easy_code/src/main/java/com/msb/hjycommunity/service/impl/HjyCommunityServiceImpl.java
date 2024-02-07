package com.msb.hjycommunity.service.impl;

import com.msb.hjycommunity.entity.HjyCommunity;
import com.msb.hjycommunity.dao.HjyCommunityDao;
import com.msb.hjycommunity.service.HjyCommunityService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 小区 (HjyCommunity)表服务实现类
 *
 * @author makejava
 * @since 2024-01-09 22:33:22
 */
@Service("hjyCommunityService")
public class HjyCommunityServiceImpl implements HjyCommunityService {
    @Resource
    private HjyCommunityDao hjyCommunityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param communityId 主键
     * @return 实例对象
     */
    @Override
    public HjyCommunity queryById(Long communityId) {
        return this.hjyCommunityDao.queryById(communityId);
    }

    /**
     * 分页查询
     *
     * @param hjyCommunity 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<HjyCommunity> queryByPage(HjyCommunity hjyCommunity, PageRequest pageRequest) {
        long total = this.hjyCommunityDao.count(hjyCommunity);
        return new PageImpl<>(this.hjyCommunityDao.queryAllByLimit(hjyCommunity, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param hjyCommunity 实例对象
     * @return 实例对象
     */
    @Override
    public HjyCommunity insert(HjyCommunity hjyCommunity) {
        this.hjyCommunityDao.insert(hjyCommunity);
        return hjyCommunity;
    }

    /**
     * 修改数据
     *
     * @param hjyCommunity 实例对象
     * @return 实例对象
     */
    @Override
    public HjyCommunity update(HjyCommunity hjyCommunity) {
        this.hjyCommunityDao.update(hjyCommunity);
        return this.queryById(hjyCommunity.getCommunityId());
    }

    /**
     * 通过主键删除数据
     *
     * @param communityId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long communityId) {
        return this.hjyCommunityDao.deleteById(communityId) > 0;
    }
}
