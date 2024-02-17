package com.msb.hjycommunity.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msb.hjycommunity.system.domain.SysDept;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TreeSelect 树形结构实体类
 */
public class TreeSelect implements Serializable {
    // 节点id
    private Long id;

    // 节点名称
    private String label;

    // 子节点
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    //构造方法接收部门对象,构建树形结构TreeSelect对象
    public TreeSelect(SysDept sysDept) {
        this.id = sysDept.getDeptId();
        this.label = sysDept.getDeptName();
        //流式处理: 通过 map 方法将子部门列表中的每个子部门转换为 TreeSelect 对象,并将其赋值给当前TreeSelect对象的 children 属性
        this.children = sysDept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
