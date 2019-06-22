package com.example.ccmyphone.ResumeClassesAndFragments;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ccmyphone.R;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.PersonalProResume;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.ResumeDBModel;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeFormats.DefaultResumeFormat;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.ccmyphone.ApplicationConstants.APPNAME;
import static com.example.ccmyphone.ApplicationConstants.EDU_QUALIFICATIONS;
import static com.example.ccmyphone.ApplicationConstants.LANGUAGES_KNOWN;
import static com.example.ccmyphone.ApplicationConstants.PERMANENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PRESENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PROFESSIONAL_DETAILS;
import static com.example.ccmyphone.ApplicationConstants.RESUME_PERSONAL;
import static com.example.ccmyphone.ApplicationConstants.USER_HOBBIES;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_DETAIL;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_EDUCATION;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_HOBBIES;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_LANGUAGES_KNOWN;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_MOBILE;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_NAME;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_PERM_ADDRESS;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_PERSONAL;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_PRESENT_ADDRESS;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_PROFESSIONAL;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.TABLE_NAME;


public class GenerateResumeFragment extends Fragment {

    String TAG = "GenerateResumeFragment";

    String dataFrom, dataUserID;
    String personalDetails, permaAddress, presentAddress, eduQualifications, professionalDetails, hobbiesStr, languagesKnown;

    public GenerateResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generated_resume, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            dataFrom = getArguments().getString(APPNAME, "");
            dataUserID = getArguments().getString("USERID", dataUserID);
            Log.d(TAG, "Data From" + dataFrom);

            if (APPNAME.equalsIgnoreCase(dataFrom)) {
                prepareResumewithSQLData(Integer.parseInt(dataUserID));
            } else {
                prepareResumewithUserData();
            }
        }

        return view;
    }

    private void prepareResumewithSQLData(int dataUserID) {
        ResumeSQLDatabase database = new ResumeSQLDatabase(getActivity());
        SQLiteDatabase SQLdb = database.getWritableDatabase();
        Cursor cursor = database.getDatabyUserId(dataUserID);
        ResumeDBModel resumeDBModel = new ResumeDBModel();

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    resumeDBModel.setId(cursor.getInt(0));
                    resumeDBModel.setName(cursor.getString(1));
                    resumeDBModel.setMobile(cursor.getString(2));
                    resumeDBModel.setDescription(cursor.getString(3));
                    resumeDBModel.setPersonal(cursor.getString(4));
                    resumeDBModel.setPermanentaddress(cursor.getString(5));
                    resumeDBModel.setPresentaddress(cursor.getString(6));
                    resumeDBModel.setEducation(cursor.getString(7));
                    resumeDBModel.setProfessional(cursor.getString(8));
                    resumeDBModel.setHobbies(cursor.getString(9));
                    resumeDBModel.setLanguagesknown(cursor.getString(10));
                } while (cursor.moveToNext());
                cursor.close();
            }

            personalDetails = resumeDBModel.getPersonal();
            permaAddress = resumeDBModel.getPermanentaddress();
            presentAddress = resumeDBModel.getPresentaddress();
            eduQualifications = resumeDBModel.getEducation();
            professionalDetails = resumeDBModel.getProfessional();
            hobbiesStr = resumeDBModel.getHobbies();
            languagesKnown = resumeDBModel.getLanguagesknown();

            DefaultResumeFormat resumeFormat = new DefaultResumeFormat();
            resumeFormat.DefaultResumeF1(getActivity(), Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getActivity().getResources().getDrawable(R.drawable.admin_icon)),
                    personalDetails, permaAddress, presentAddress, eduQualifications, professionalDetails, hobbiesStr, languagesKnown);
            Log.d(TAG, "Bundles " + personalDetails + "\n" + permaAddress + "\n" + presentAddress + "\n" + eduQualifications
                    + "\n" + professionalDetails + "\n" + hobbiesStr + "\n" + languagesKnown);
            ResumePreviewFragment previewFragment = new ResumePreviewFragment();
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, previewFragment)
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).commit();
            }
        }
        database.close();
    }

    private void prepareResumewithUserData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            personalDetails = getArguments().getString(RESUME_PERSONAL, "");
            permaAddress = getArguments().getString(PERMANENT_ADDRESS, "");
            presentAddress = getArguments().getString(PRESENT_ADDRESS, "");
            eduQualifications = getArguments().getString(EDU_QUALIFICATIONS, "");
            professionalDetails = getArguments().getString(PROFESSIONAL_DETAILS, "");
            hobbiesStr = getArguments().getString(USER_HOBBIES, "");
            languagesKnown = getArguments().getString(LANGUAGES_KNOWN, "");
        }
        Log.d(TAG, "Bundles " + personalDetails + "\n" + permaAddress + "\n" + presentAddress + "\n" + eduQualifications
                + "\n" + professionalDetails + "\n" + hobbiesStr + "\n" + languagesKnown);

        ResumeSQLDatabase database = new ResumeSQLDatabase(getActivity());
        SQLiteDatabase SQLdb = database.getWritableDatabase();

        Gson gson = new Gson();
        PersonalProResume personalProResume = new PersonalProResume();
        personalProResume = gson.fromJson(personalDetails, PersonalProResume.class);
        ArrayList<PersonalProResume> proResumes = new ArrayList<>();
        proResumes.add(personalProResume);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, personalProResume.getFullName());
        contentValues.put(COL_MOBILE, personalProResume.getMobileNo());
        contentValues.put(COL_DETAIL, personalProResume.getNickName());
        contentValues.put(COL_PERSONAL, personalDetails);
        contentValues.put(COL_PERM_ADDRESS, permaAddress);
        contentValues.put(COL_PRESENT_ADDRESS, presentAddress);
        contentValues.put(COL_EDUCATION, eduQualifications);
        contentValues.put(COL_PROFESSIONAL, professionalDetails);
        contentValues.put(COL_HOBBIES, hobbiesStr);
        contentValues.put(COL_LANGUAGES_KNOWN, languagesKnown);

        long rowInserted = SQLdb.insert(TABLE_NAME, null, contentValues);
        if (rowInserted != -1) {
            Toast.makeText(getActivity(), "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
            DefaultResumeFormat resumeFormat = new DefaultResumeFormat();
            resumeFormat.DefaultResumeF1(getActivity(), Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getActivity().getResources().getDrawable(R.drawable.admin_icon)),
                    personalDetails, permaAddress, presentAddress, eduQualifications, professionalDetails, hobbiesStr, languagesKnown);
            ResumePreviewFragment previewFragment = new ResumePreviewFragment();
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, previewFragment)
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).commit();
            }
        } else {
            Toast.makeText(getActivity(), "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show();
        }
    }


}
