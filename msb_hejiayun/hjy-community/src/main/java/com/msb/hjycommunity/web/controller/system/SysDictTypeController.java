package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysDictType;
import com.msb.hjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Autowired
    private SysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @GetMapping("/list")
    public PageResult list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getData(list);
    }

    /**
     * 根据Id查询字典类型详细信息
     */
    @GetMapping(value = "/{dictId}")
    public BaseResponse getInfo(@PathVariable Long dictId) {
        return BaseResponse.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictType dictType) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return BaseResponse.fail("新增字典" + dictType.getDictName() + "失败，字典类型已存在");
        }
        dictType.setCreateBy(SecurityUtils.getUserName());
        return toAjax(dictTypeService.insertDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictType dictType) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return BaseResponse.fail("修改字典" + dictType.getDictName() + "失败，字典类型已存在");
        }
        dictType.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(dictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public BaseResponse remove(@PathVariable Long[] dictIds) {
        return toAjax(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     */
    @DeleteMapping("/clearCache")
    public BaseResponse clearCache() {
        dictTypeService.clearCache();
        return BaseResponse.success("清除缓存成功");
    }
}
