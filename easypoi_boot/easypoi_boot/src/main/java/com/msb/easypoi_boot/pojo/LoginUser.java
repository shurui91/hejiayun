package com.msb.easypoi_boot.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelTarget("loginUser")
public class LoginUser implements Serializable {

    @Excel(name = "用户ID", orderNum = "1")
    private String id;

    @Excel(name = "昵称", orderNum = "2")
    private String nickname;

    @Excel(name = "密码", orderNum = "3")
    private String password;

    @Excel(name = "注册时间", orderNum = "4", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Excel(name = "状态", orderNum = "5", replace = {"VIP_1", "普通用户_0"})
    private String status;

    public LoginUser() {
    }

    public LoginUser(String id, String nickname, String password, Date createTime, String status) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.createTime = createTime;
        this.status = status;
    }
}
