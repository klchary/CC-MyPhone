package com.ccapps.ccmyphone.myphone.OtherClasses;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public File CheckDirectory(Context context, String fileDir) {
        File mainDir = new File(fileDir);
        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                Log.e("Create Directory", "Main Directory Created : " + mainDir);
        }
        return mainDir;
    }

    public boolean CheckValidName(String name, TextInputLayout tilName) {
        boolean isValidName;
        if (TextUtils.isEmpty(name)) {
            tilName.setError("Please Enter Name");
            isValidName = false;
        } else if (!isNameValid(name)) {
            tilName.setError("Name Charecters must be 3 - 15");
            isValidName = false;
        } else {
            isValidName = true;
        }
        return isValidName;
    }

    public boolean CheckValidMobile(String mobileNoStr, TextInputLayout tilMobile) {
        boolean isValidMobile;
        if (TextUtils.isEmpty(mobileNoStr)) {
            tilMobile.setError("Please Enter Valid Mobile Number");
            isValidMobile = false;
        } else if (!isMobileValid(mobileNoStr)) {
            tilMobile.setError("Please Enter Your 10 digit Mobile Number");
            isValidMobile = false;
        } else {
            isValidMobile = true;
        }

        return isValidMobile;
    }

    public boolean CheckValidEmail(String emailIdStr, TextInputLayout tilEmail) {
        boolean isValidMobile;
        if (TextUtils.isEmpty(emailIdStr)) {
            tilEmail.setError("Please Enter Valid Email ID");
            isValidMobile = false;
        } else if (!isEmailValid(emailIdStr)) {
            tilEmail.setError("Please Enter Valid Email ID");
            isValidMobile = false;
        } else {
            isValidMobile = true;
        }

        return isValidMobile;
    }

    public boolean CheckValidDOB(String dobStr, TextInputLayout tilDoB) {
        boolean isValidMobile;
        if (TextUtils.isEmpty(dobStr)) {
            tilDoB.setError("Please Enter Valid Date of Birth");
            isValidMobile = false;
        } else if (!isValidDateFormat(dobStr)) {
            tilDoB.setError("Please Enter Date of Birth in DD/MM/YYYY");
            isValidMobile = false;
        } else {
            isValidMobile = true;
        }

        return isValidMobile;
    }

    public boolean isNameValid(String name) {
        return name.length() > 3 && name.length() < 15;
    }

    public boolean isMobileValid(String mobile) {
        return mobile.length() == 10 && mobile.matches("[0-9]+");
    }

    private boolean isEmailValid(String emailid) {
        return emailid.contains("@") && emailid.contains(".com") || emailid.contains(".in");
    }

    public boolean isPasswordValid(String password) {
        return (password.length() >= 6 && password.length() <= 15);
    }

    private boolean isValidDateFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

}
