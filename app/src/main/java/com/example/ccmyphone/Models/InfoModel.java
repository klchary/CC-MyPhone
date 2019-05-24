package com.example.ccmyphone.Models;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public class InfoModel {

    private String infoTitle, infoDetail, infoDetailText;

//    public InfoModel(String infoTitle, String infoDetail) {
//        this.infoTitle = infoTitle;
//        this.infoDetail = infoDetail;
//    }

    public String getInfoDetailText() {
        return infoDetailText;
    }

    public void setInfoDetailText(String infoDetailText) {
        this.infoDetailText = infoDetailText;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        infoTitle = infoTitle;
    }

    public String getInfoDetail() {
        return infoDetail;
    }

    public void setInfoDetail(String infoDetail) {
        infoDetail = infoDetail;
    }
}
