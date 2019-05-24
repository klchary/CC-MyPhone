package com.example.ccmyphone.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class AppsListModel {

    private String appName;
    private Drawable appIcon;
    private String installedTime;
    private String updateTime;
    private String packageName;
    private String permissions;


    public AppsListModel(String appName, Drawable appIcon, String installedTime,
                         String updateTime, String packageName, String permissions) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.installedTime = installedTime;
        this.updateTime = updateTime;
        this.packageName = packageName;
        this.permissions = permissions;

    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInstalledTime() {
        return installedTime;
    }

    public void setInstalledTime(String installedTime) {
        this.installedTime = installedTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
