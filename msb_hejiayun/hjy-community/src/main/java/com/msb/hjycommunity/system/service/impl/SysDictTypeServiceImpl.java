package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.constant.RedisCache;
import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.exception.CustomException;
import com.msb.hjycommunity.system.domain.SysDictData;
import com.msb.hjycommunity.system.domain.SysDictType;
import com.msb.hjycommunity.system.mapper.SysDictDataMapper;
import com.msb.hjycommunity.system.mapper.SysDictTypeMapper;
import com.msb.hjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author spikeCong
 * @date 2023/5/19
 **/
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        // 获取字典类型数据
        List<SysDictType> typeList = dictTypeMapper.selectDictTypeAll();
        for (SysDictType dictType : typeList) {
            List<SysDictData> dictData = dictDataMapper.selectDictDataByType(dictType.getDictType());
            redisCache.setCacheObject(getCacheKey(dictType.getDictType()), dictData);
        }
    }

    private String getCacheKey(String key) {
        return Constants.SYS_DICT_KEY + key;
    }

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        // 先从缓存获取
        List<SysDictData> dictData = redisCache.getCacheObject(getCacheKey(dictType));
        if (!Objects.isNull(dictData)) {
            return dictData;
        }
        // 缓存没有，查询数据库
        dictData = dictDataMapper.selectDictDataByType(dictType);
        if (!Objects.isNull(dictData)) {
            redisCache.setCacheObject(getCacheKey(dictType), dictData);
            return dictData;
        }
        return null;
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询字典类型
     *
     * @param dictType 字典类型
     * @return
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除
     *
     * @param dictIds 需要删除的字典ID
     * @return
     */
    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType sysDictType = selectDictTypeById(dictId);
            // 判断该类型下是否有对应的字典数据
            if (dictDataMapper.countDictDataByType(sysDictType.getDictType()) > 0) {
                throw new CustomException("已分配不能删除", 500);
            }
        }
        int row = dictTypeMapper.deleteDictTypeByIds(dictIds);
        // 删除成功，清除缓存
        if (row > 0) {
            clear();
        }
        return row;
    }

    /**
     * 清空缓存
     */
    private void clear() {
        Collection<String> keys = redisCache.keys(Constants.SYS_DICT_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型信息
     * @return
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        int row = dictTypeMapper.insertDictType(dictType);
        if (row > 0) {
            clear();
        }
        return row;
    }

    /**
     * 修改字典类型信息
     *
     * @param dictType
     * @return: int
     */
    @Override
    @Transactional //执行多条SQL,添加事务注解
    public int updateDictType(SysDictType dictType) {
        // 修改字典数据表的字典类型
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());

        // 修改字典类型表
        int row = dictTypeMapper.updateDictType(dictType);
        if (row > 0) {
            clear();
        }
        return row;
    }

    /**
     * 校验字典类型是否唯一
     *
     * @param dictType
     * @return
     */
    @Override
    public String checkDictTypeUnique(SysDictType dictType) {
        SysDictType sysDictType = dictTypeMapper.checkDictTypeUnique(dictType.getDictType());
        if (!Objects.isNull(sysDictType)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void clearCache() {
        clear();
    }
}
