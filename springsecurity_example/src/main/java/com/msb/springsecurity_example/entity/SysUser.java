package com.msb.springsecurity_example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId
    private Long userId;
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String sex;
    private String status;

    public SysUser() {
    }

    public SysUser(Long userId, String userName, String nickName, String password, String phonenumber, String sex, String status) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
