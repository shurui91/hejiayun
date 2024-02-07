package com.msb.springsecurity_example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -3786809084085483513L;
    // 角色ID
    @TableId
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态 0正常,1停用
     */
    private String status;

    /**
     * 删除标志 0存在,1删除
     */
    private String delFlag;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private String remark;
}
