package com.example.ccmyphone.Models;

public class InfoModel {

    private String InfoTitle, InfoDetail;

    public InfoModel (String infoTitle, String infoDetail){
        this.InfoTitle = infoTitle;
        this.InfoDetail = infoDetail;
    }

    public String getInfoTitle() {
        return InfoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        InfoTitle = infoTitle;
    }

    public String getInfoDetail() {
        return InfoDetail;
    }

    public void setInfoDetail(String infoDetail) {
        InfoDetail = infoDetail;
    }
}
