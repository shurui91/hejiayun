package com.msb.easypoi_boot.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelTarget("emps")
public class Emp implements Serializable {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "生日", format = "yyyy-MM-dd HH:mm:ss")
    private Date bir;
    @Excel(name = "状态", replace = {"激活_1", "未激活_0"})
    private String status;

    @Excel(name = "头像信息", type = 2, savePath = "/Users/user/Downloads/msb_hejiayun/easypoi_boot/easypoi_boot/src/main/resources/static")
    private String photo;
}
