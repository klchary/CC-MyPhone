package com.example.ccmyphone;

import com.example.ccmyphone.Models.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public interface ApplicationConstants {

    String APPNAME = "CC MyPhone";

    UserDetails userDetails_All = new UserDetails();

    String FIREBASE_PROJECT_NUMBER = "610100917097";
    String FIREBASE_URL = "https://cc-myphone.firebaseio.com";
    String FIREBASE_PROJECT_ID = "cc-myphone";

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef_Users = FirebaseDatabase.getInstance().getReference().child("USERS");

    String ADMOB_APPID = "ca-app-pub-2479880548676052~2800021293";
    String ADMOB_ADUNIT_ID_GUEST_USER_BANNER = "ca-app-pub-2479880548676052/9441930191";
    String ADMOB_ADUNIT_ID_GUEST_USER_FULL = "ca-app-pub-2479880548676052/2842407416";
    String ADMOB_TESTUNIT_ID = "ca-app-pub-3940256099942544/6300978111";

    boolean TRUE = true;
    boolean FALSE = false;

    String INFO_TITLE = "infoTitle";
    String INFO_DETAIL = "infoDetail";
    String INFO_DETAIL_TEXT = "infoDetailText";

    String SHARED_PERSISTENT_VALUES = "Persistent_Values";
    String USER_DETAILS = "userdetails";

    String REG_ERROR = "Something went wrong, Please try again after some time";
    String ALREADY_REG_ERROR = "Already registered with given Credentials, Please Try again or Register";
    String REG_SUCCESS = "Registration Successful, Please Login to Continue";
    String REG_TO_LOGIN = "LOGIN";

    String OK = "OK";

}
