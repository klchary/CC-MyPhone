package com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity;

/**
 * Created by CHINNA CHARY on Saturday, 22 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity
 **/
public class ResumeDBModel {

    private int id;
    private String name;
    private String mobile;
    private String description;
    private String personal;
    private String permanentaddress;
    private String presentaddress;
    private String education;
    private String professional;
    private String hobbies;
    private String languagesknown;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getPermanentaddress() {
        return permanentaddress;
    }

    public void setPermanentaddress(String permanentaddress) {
        this.permanentaddress = permanentaddress;
    }

    public String getPresentaddress() {
        return presentaddress;
    }

    public void setPresentaddress(String presentaddress) {
        this.presentaddress = presentaddress;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getLanguagesknown() {
        return languagesknown;
    }

    public void setLanguagesknown(String languagesknown) {
        this.languagesknown = languagesknown;
    }
}
