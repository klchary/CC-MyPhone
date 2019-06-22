package com.example.ccmyphone.ResumeClassesAndFragments;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.EduQualifications;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeFormats.DefaultResumeFormat;
import com.example.ccmyphone.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.ccmyphone.ApplicationConstants.EDU_QUALIFICATIONS;
import static com.example.ccmyphone.ApplicationConstants.PERMANENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PRESENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.RESUME_PERSONAL;
import static com.example.ccmyphone.ApplicationConstants.RESUME_SHARED_VALUES;

/**
 * A simple {@link Fragment} subclass.
 */
public class EducationalFragment extends Fragment {

    String TAG = "EducationalFragment";

    LinearLayout eduQuaLayout;
    ImageView addnewQuaLayout;
    Button btnBack, btnNext;

    // Child Layout Views
    LinearLayout eduQuaAddLayout;
    TextView eduTitle;
    EditText eduIntstituteName, eduIntstituteDist, eduIntstituteState, eduCourseFrom, eduCourseName, eduCourseTo, eduPercentage;

    Gson gson;
    JSONObject jsonObject;
    JSONArray qualificationArray;

    SharedPreferences preferences;

    public EducationalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_educational, container, false);
        AddChildEduQuaLayout(view);
        FindAllViews(view);
        preferences = getActivity().getSharedPreferences(RESUME_SHARED_VALUES, Context.MODE_PRIVATE);
        String eduQualifications = preferences.getString(EDU_QUALIFICATIONS, "");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= eduQuaLayout.getChildCount(); i++) {
                    eduQuaAddLayout = view.findViewById(R.id.eduQuaAddLayout);
                    eduQuaAddLayout.setId(i);
                    for (int j = 1; j < eduQuaAddLayout.getChildCount(); j++) {
                        LinearLayout child = (LinearLayout) eduQuaAddLayout.getChildAt(i);
                        eduTitle = eduQuaAddLayout.findViewById(R.id.eduTitle);
                        eduIntstituteName = eduQuaAddLayout.findViewById(R.id.eduIntstituteName);
                        eduIntstituteDist = eduQuaAddLayout.findViewById(R.id.eduIntstituteDist);
                        eduIntstituteState = eduQuaAddLayout.findViewById(R.id.eduIntstituteState);
                        eduCourseName = eduQuaAddLayout.findViewById(R.id.eduCourseName);
                        eduCourseFrom = eduQuaAddLayout.findViewById(R.id.eduCourseFrom);
                        eduCourseTo = eduQuaAddLayout.findViewById(R.id.eduCourseTo);
                        eduPercentage = eduQuaAddLayout.findViewById(R.id.eduPercentage);

                        int layoutId = eduQuaAddLayout.getId();
                        String quaTitle = eduTitle.getText().toString().trim();
                        String courseName = eduCourseName.getText().toString().trim();
                        String courseFrom = eduCourseFrom.getText().toString().trim();
                        String courseTo = eduCourseTo.getText().toString().trim();
                        String percentage = eduPercentage.getText().toString().trim();
                        String institutionName = eduIntstituteName.getText().toString().trim();
                        String instiDist = eduIntstituteDist.getText().toString().trim();
                        String instiState = eduIntstituteState.getText().toString().trim();

                        EduQualifications qualifications = new EduQualifications();
                        qualifications.setLayoutId(layoutId);
                        qualifications.setQualificationTitle(quaTitle);
                        qualifications.setCourseName(courseName);
                        qualifications.setCoureFrom(courseFrom);
                        qualifications.setCourseTo(courseTo);
                        qualifications.setPercentage(Integer.parseInt(percentage));
                        qualifications.setInstitutionName(institutionName);
                        qualifications.setInstitutionDist(instiDist);
                        qualifications.setInstitutionState(instiState);

                        String object = gson.toJson(qualifications);
                        try {
                            jsonObject = new JSONObject(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    qualificationArray.put(jsonObject);
                    Log.d(TAG, "Qua qualificationArray " + qualificationArray);

                }
                SharedPreferences.Editor editor = preferences.edit();
                Bundle bundle = new Bundle();
                bundle.putString(EDU_QUALIFICATIONS, qualificationArray.toString());
                Log.d(TAG, "Qua qualificationArray String " + qualificationArray.toString());
                editor.putString(EDU_QUALIFICATIONS, qualificationArray.toString()).apply();
                Bundle completeBundle = getArguments();
                if (completeBundle != null) {
                    String personalDetails = getArguments().getString(RESUME_PERSONAL);
                    String permaAddress = getArguments().getString(PERMANENT_ADDRESS);
                    String presentAddress = getArguments().getString(PRESENT_ADDRESS);
                    bundle.putString(RESUME_PERSONAL, personalDetails);
                    bundle.putString(PERMANENT_ADDRESS, permaAddress);
                    bundle.putString(PRESENT_ADDRESS, presentAddress);
                }
                ProfessionalFragment professionalFragment = new ProfessionalFragment();
                professionalFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, professionalFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This Feature is disabled for demo version", Toast.LENGTH_LONG).show();
//                AddressFragment addressFragment = new AddressFragment();
//                if (getFragmentManager() != null) {
//                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, addressFragment)
//                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).commit();
//                }
            }
        });

        addnewQuaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddChildEduQuaLayout(view);
            }
        });

        return view;
    }

    private void AddChildEduQuaLayout(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        eduQuaLayout = view.findViewById(R.id.eduQuaLayout);
        View childView = inflater.inflate(R.layout.edu_qualification_add_layout, null);
        eduQuaLayout.addView(childView);
    }

    private void FindAllViews(View view) {
        gson = new Gson();
        qualificationArray = new JSONArray();

        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        addnewQuaLayout = view.findViewById(R.id.addnewQuaLayout);
    }

}
