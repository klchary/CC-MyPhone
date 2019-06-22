package com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity;

/**
 * Created by CHINNA CHARY on Monday, 17 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.Models
 **/
public class LanguagesKnownModel {

    private String languageName;
    private boolean isRead;
    private  boolean isWrite;
    private boolean isSpeak;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public void setWrite(boolean write) {
        isWrite = write;
    }

    public boolean isSpeak() {
        return isSpeak;
    }

    public void setSpeak(boolean speak) {
        isSpeak = speak;
    }
}
