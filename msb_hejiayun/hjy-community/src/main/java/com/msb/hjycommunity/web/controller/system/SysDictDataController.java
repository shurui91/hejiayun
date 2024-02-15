package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysDictData;
import com.msb.hjycommunity.system.service.SysDictDataService;
import com.msb.hjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private SysDictDataService dictDataService;

    @Autowired
    private SysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @RequestMapping("/list")
    public PageResult list(SysDictData sysDictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        return getData(list);
    }

    /**
     * 根据id查询字典详细信息
     */
    @GetMapping(value = "/{dictCode}")
    public BaseResponse getInfo(@PathVariable Long dictCode) {
        return BaseResponse.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public BaseResponse getDictByType(@PathVariable String dictType) {
        return BaseResponse.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictData dictData) {
        dictData.setCreateBy(SecurityUtils.getUserName());
        return toAjax(dictDataService.insertDictData(dictData));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictData dictData) {
        dictData.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(dictDataService.updateDictData(dictData));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public BaseResponse remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
