package com.msb.easypoi_boot.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

@ExcelTarget("card")   // 标识当前类是用于excel导入导出
@Data
public class Card implements Serializable {
    @Excel(name = "身份证号", orderNum = "9")
    private String id;
    @Excel(name = "家庭住址", orderNum = "10")
    private String address;

    public Card(String id, String address) {
        this.id = id;
        this.address = address;
    }
}
