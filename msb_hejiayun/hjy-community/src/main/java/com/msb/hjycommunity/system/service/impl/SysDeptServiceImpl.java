package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.common.core.exception.CustomException;
import com.msb.hjycommunity.system.domain.SysDept;
import com.msb.hjycommunity.system.mapper.SysDeptMapper;
import com.msb.hjycommunity.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     *
     * @param sysDept
     * @return
     */
    @Override
    public List<SysDept> selectDeptList(SysDept sysDept) {
        return deptMapper.selectDeptList(sysDept);
    }

    /**
     * 根据ID查询部门信息
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public int insertDept(SysDept dept) {
        //1. 获取当前节点的父节点
        SysDept parentDept = deptMapper.selectDeptById(dept.getParentId());
        //2. 判断父节点是不是正常状态，非正常状态不允许插入
        //有可能父节点已经被停用
        if (!UserConstants.DEPT_NORMAL.equals(parentDept.getStatus())) {
            throw new CustomException("部门停用，不允许新增", 500);
        }
        //3. 设置组级列表字段的值
        //0,100,101
        dept.setAncestors(parentDept.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    @Override
    public int updateDept(SysDept dept) {
        //获取新的父节点
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        //获取旧节点数据
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (!Objects.isNull(newParentDept) && !Objects.isNull(oldDept)) {
            //设置最新的组级列表
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            dept.setAncestors(newAncestors);

            //修改子元素的关系
            String oldAncestors = oldDept.getAncestors();
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素的关系
     *
     * @param deptId       被修改的部门
     * @param newAncestors 新的父id的集合
     * @param oldAncestors 旧的父id的集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> childrenDept = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : childrenDept) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (childrenDept.size() > 0) {
            deptMapper.updateDeptChildren(childrenDept);
        }
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public String checkDeptNameUnique(SysDept dept) {
        SysDept sysDept = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (!Objects.isNull(sysDept)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 判断是否存在字节点
     *
     * @param deptId
     * @return
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int row = deptMapper.hasChildByDeptId(deptId);
        return row > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int row = deptMapper.checkDeptExistUser(deptId);
        return row > 0;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param sysDepts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> deptList) {
        List<SysDept> deptTrees = buildDeptTree(deptList);
        List<TreeSelect> selectList = deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
        return selectList;
    }

    /**
     * 构建部门树结构
     *
     * @param sysDepts
     * @return:
     */
    private List<SysDept> buildDeptTree(List<SysDept> deptList) {
        //要返回的list
        List<SysDept> res = new ArrayList<>();

        //获取所有部门的deptId,保存到List
        List<Long> idList = new ArrayList<>();
        for (SysDept sysDept : deptList) {
            idList.add(sysDept.getDeptId());
        }

        deptList.stream()
                //如果是顶级节点,遍历该节点下的所有子节点
                .filter(dept -> !idList.contains(dept.getParentId()))
                .forEach(dept -> {
                    //递归获取子节点
                    helper(deptList, dept);
                    res.add(dept);
                });
        return res;
    }

    /**
     * 递归操作
     *
     * @param sysDepts 部门集合
     * @param dept     父部门
     */
    private void helper(List<SysDept> deptList, SysDept dept) {
        //得到子节点
        List<SysDept> childList = getChildList(deptList, dept);
        dept.setChildren(childList);
        for (SysDept child : childList) {
            //判断是否还有子节点
            if (hasChild(childList, child)) {
                //递归调用
                helper(deptList, child);
            }
        }
    }

    /**
     * 得到子节点列表
     *
     * @param sysDepts
     * @param dept
     * @return:
     */
    private List<SysDept> getChildList(List<SysDept> sysDepts, SysDept dept) {
        List<SysDept> subList = new ArrayList<>();
        subList = sysDepts.stream()
                .filter(subDept -> !Objects.isNull(subDept.getParentId())
                        && subDept.getParentId().longValue() == dept.getDeptId().longValue())
                .collect(Collectors.toList());
        return subList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept dept) {
        return getChildList(list, dept).size() > 0 ? true : false;
    }
}
