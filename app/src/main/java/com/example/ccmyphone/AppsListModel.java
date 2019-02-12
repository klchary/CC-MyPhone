package com.example.ccmyphone;

import android.graphics.drawable.Drawable;

public class AppsListModel {

    private String appName;
    private Drawable appIcon;
    private String installedTime;
    private String updateTime;

    public AppsListModel(String appName, Drawable appIcon, String installedTime, String updateTime) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.installedTime = installedTime;
        this.updateTime = updateTime;
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
