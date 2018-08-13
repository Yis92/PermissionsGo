package com.yis.study.model;

import java.io.Serializable;

/**
 * Created by yis on 2018/8/10.
 * 动态申请权限实体类
 */
public class MyPermissionBean implements Serializable {

    private boolean isHas;
    private String permissionName;
    private String permissionRequestInfo;

    public boolean isHas() {
        return isHas;
    }

    public MyPermissionBean setHas(boolean has) {
        isHas = has;
        return this;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public MyPermissionBean setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    public String getPermissionRequestInfo() {
        return permissionRequestInfo;
    }

    public MyPermissionBean setPermissionRequestInfo(String permissionRequestInfo) {
        this.permissionRequestInfo = permissionRequestInfo;
        return this;
    }
}
