package com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CHINNA CHARY on Friday, 21 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils
 **/
public class ResumeSQLDatabase extends SQLiteOpenHelper {

    private final String TAG = "ResumeSQLDatabase";

    public static final String DATABASE_NAME = "CC_DATABASE";
    public static final String TABLE_NAME = "ResumeTable";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_DETAIL = "description";
    public static final String COL_PERSONAL = "personal";
    public static final String COL_PERM_ADDRESS = "permanentaddress";
    public static final String COL_PRESENT_ADDRESS = "presentaddress";
    public static final String COL_EDUCATION = "education";
    public static final String COL_PROFESSIONAL = "professional";
    public static final String COL_HOBBIES = "hobbies";
    public static final String COL_LANGUAGES_KNOWN = "languagesknown";
    public static final int VERSION = 1;

    public ResumeSQLDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDataBase = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_MOBILE + " TEXT, "
                + COL_DETAIL + " TEXT, "
                + COL_PERSONAL + " TEXT, "
                + COL_PERM_ADDRESS + " TEXT, "
                + COL_PRESENT_ADDRESS + " TEXT, "
                + COL_EDUCATION + " TEXT, "
                + COL_HOBBIES + " TEXT, "
                + COL_LANGUAGES_KNOWN + " TEXT, "
                + COL_PROFESSIONAL + " TEXT" + ")";
        db.execSQL(createDataBase);
        Log.d(TAG, "Creating Table " + createDataBase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        String droptableSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(droptableSQL);
        // Create tables again
        onCreate(db);
        Log.d(TAG, "Drop Table if Exists " + droptableSQL);
    }

    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getDatabyUserId(int userid) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM ResumeTable WHERE id='" + userid + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

}
