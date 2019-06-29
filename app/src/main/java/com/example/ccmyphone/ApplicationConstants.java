package com.example.ccmyphone;

import android.os.Environment;

import com.example.ccmyphone.Models.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

public interface ApplicationConstants {

    String APPNAME = "CC MyPhone";

    String CC_MyPhone_PATH = Environment.getExternalStorageDirectory().toString() + "/CC MyPhone";

    UserDetails USER_DETAILS_ALL = new UserDetails();

    String FIREBASE_PROJECT_NUMBER = "610100917097";
    String FIREBASE_URL = "https://cc-myphone.firebaseio.com";
    String FIREBASE_PROJECT_ID = "cc-myphone";

    DatabaseReference DATABASE_REF = FirebaseDatabase.getInstance().getReference();
    DatabaseReference DATABASE_REF_USERS = FirebaseDatabase.getInstance().getReference().child("USERS");

    String ADMOB_APPID = "ca-app-pub-2479880548676052~2800021293";
    String ADMOB_ADUNIT_ID_GUEST_USER_BANNER = "ca-app-pub-2479880548676052/9441930191";
    String ADMOB_ADUNIT_ID_GUEST_USER_FULL = "ca-app-pub-2479880548676052/2842407416";
    String ADMOB_TESTUNIT_ID = "ca-app-pub-3940256099942544/6300978111";

    String APPID = "2366302220";
    String ADUNIT_ID_FULL = "2366300712";
    String ADUNIT_ID_BANNER = "2366329210";

    boolean TRUE = true;
    boolean FALSE = false;

    String INFO_TITLE = "infoTitle";
    String INFO_DETAIL = "infoDetail";
    String INFO_DETAIL_TEXT = "infoDetailText";

    String SHARED_PERSISTENT_VALUES = "Persistent_Values";
    String USER_DETAILS = "userdetails";

    String REG_ERROR = "Something went wrong, Please try again after some time";
    String ALREADY_REG_ERROR = "Already registered with given Credentials, Please Try to Login or Register";
    String REG_SUCCESS = "Registration Successful, Please Login to Continue";
    String REG_TO_LOGIN = "LOGIN";

    String OK = "OK";

}
