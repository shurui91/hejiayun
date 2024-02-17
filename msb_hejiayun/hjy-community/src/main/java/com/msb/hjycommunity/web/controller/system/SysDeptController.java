package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysDept;
import com.msb.hjycommunity.system.service.SysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Resource
    private SysDeptService deptService;

    @GetMapping("/list")
    @PreAuthorize("@pe.hasPerms('system:dept:list')")
    public BaseResponse findDeptList(SysDept sysDept) {
        List<SysDept> sysDepts = deptService.selectDeptList(sysDept);
        return BaseResponse.success(sysDepts);
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param deptId
     * @return
     */
    @GetMapping(value = "/{deptId}")
    public BaseResponse getInfo(@PathVariable Long deptId) {
        return BaseResponse.success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     *
     * @param sysDept
     * @return
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDept sysDept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(sysDept))) {
            return BaseResponse.fail("新增部门" + sysDept.getDeptName() + "失败,部门名称已经存在");
        }
        sysDept.setCreateBy(SecurityUtils.getUserName());
        return toAjax(deptService.insertDept(sysDept));
    }

    /**
     * 修改部门
     *
     * @param dept
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return BaseResponse.fail("修改部门" + dept.getDeptName() + "失败,部门名称已经存在");
        } else if (dept.getParentId().equals(dept.getParentId())) {
            return BaseResponse.fail("修改部门" + dept.getDeptName() + "失败,上级部门不能是自己");
        }
        dept.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     *
     * @param deptId
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse
     */
    @DeleteMapping("/{deptId}")
    public BaseResponse remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return BaseResponse.fail("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return BaseResponse.fail("部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 获取部门下拉列表
     */
    @GetMapping("/treeselect")
    public BaseResponse treeSelect(SysDept sysDept) {
        List<SysDept> sysDepts = deptService.selectDeptList(sysDept);
        return BaseResponse.success(deptService.buildDeptTreeSelect(sysDepts));
    }

    /**
     * 查询部门列表 (排除节点)
     */
    @GetMapping("/list/exclude/{deptId}")
    public BaseResponse excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> deptList = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> iterator = deptList.iterator();
        while (iterator.hasNext()) {
            SysDept d = (SysDept) iterator.next();
            if (d.getDeptId().intValue() == deptId ||
                    ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                iterator.remove();
            }
        }
        return BaseResponse.success(deptList);
    }
}
