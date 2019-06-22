package com.example.ccmyphone.ResumeClassesAndFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.ProfessionalExpModel;
import com.example.ccmyphone.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.ccmyphone.ApplicationConstants.EDU_QUALIFICATIONS;
import static com.example.ccmyphone.ApplicationConstants.LANGUAGES_KNOWN;
import static com.example.ccmyphone.ApplicationConstants.PERMANENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PRESENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PROFESSIONAL_DETAILS;
import static com.example.ccmyphone.ApplicationConstants.RESUME_PERSONAL;
import static com.example.ccmyphone.ApplicationConstants.USER_HOBBIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessionalFragment extends Fragment {

    String TAG = "ProfessionalFragment";

    CheckBox fresherCheckbox;
    ImageView addnewExpLayout;
    LinearLayout professionalExpLayout;
    Button btnBack, btnNext;
    Boolean isFresher;
    ProfessionalExpModel professionalExpModel;

    Gson gson;
    JSONObject jsonObject;
    JSONArray professionalArray;

    LinearLayout professionalAddLayout;
    TextView workTitle;
    EditText workOfficeName, workOfficeDist, workOfficeState, workRoleName, workFrom, workTo, experience;

    public ProfessionalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_professional, container, false);
        FindAllViews(view);
        isFresher = false;
        AddChildProfessionalLayout(view);

        fresherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addnewExpLayout.setVisibility(View.GONE);
                    isFresher = true;
                } else {
                    addnewExpLayout.setVisibility(View.VISIBLE);
                    isFresher = false;
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFresher) {
                    professionalExpModel.setWorkType("Fresher");
                } else {
                    for (int i = 1; i <= professionalExpLayout.getChildCount(); i++) {
                        professionalAddLayout = view.findViewById(R.id.professionalAddLayout);
                        professionalAddLayout.setId(i);
                        for (int j = 1; j < professionalAddLayout.getChildCount(); j++) {
                            LinearLayout child = (LinearLayout) professionalAddLayout.getChildAt(i);
                            workTitle = professionalAddLayout.findViewById(R.id.workTitle);
                            workOfficeName = professionalAddLayout.findViewById(R.id.workOfficeName);
                            workOfficeDist = professionalAddLayout.findViewById(R.id.workOfficeDist);
                            workOfficeState = professionalAddLayout.findViewById(R.id.workOfficeState);
                            workRoleName = professionalAddLayout.findViewById(R.id.workRoleName);
                            workFrom = professionalAddLayout.findViewById(R.id.workFrom);
                            workTo = professionalAddLayout.findViewById(R.id.workTo);
                            experience = professionalAddLayout.findViewById(R.id.experience);

                            int layoutId = professionalAddLayout.getId();
                            String workTitleStr = workTitle.getText().toString().trim();
                            String workOfficeNameStr = workOfficeName.getText().toString().trim();
                            String workOfficeDistStr = workOfficeDist.getText().toString().trim();
                            String workOfficeStateStr = workOfficeState.getText().toString().trim();
                            String workRoleNameStr = workRoleName.getText().toString().trim();
                            String workFromStr = workFrom.getText().toString().trim();
                            String workToStr = workTo.getText().toString().trim();
                            String experienceStr = experience.getText().toString().trim();

                            professionalExpModel.setWorkType("Experienced");
                            professionalExpModel.setLayoutId(layoutId);
                            professionalExpModel.setWorkTitle(workTitleStr);
                            professionalExpModel.setWorkOfficeName(workOfficeNameStr);
                            professionalExpModel.setWorkOfficeDist(workOfficeDistStr);
                            professionalExpModel.setWorkOfficeState(workOfficeStateStr);
                            professionalExpModel.setWorkExperience(Integer.parseInt(experienceStr));
                            professionalExpModel.setWorkRoleName(workRoleNameStr);
                            professionalExpModel.setWorkFrom(workFromStr);
                            professionalExpModel.setWorkTo(workToStr);

                            String object = gson.toJson(professionalExpModel);
                            try {
                                jsonObject = new JSONObject(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        professionalArray.put(jsonObject);
                        Log.d(TAG, "Qua qualificationArray " + professionalArray);
                    }
                }
                Bundle bundle = new Bundle();
                Bundle receivedBundle = getArguments();
                bundle.putString(PROFESSIONAL_DETAILS, professionalArray.toString());
                if (receivedBundle != null) {
                    String personalDetails = getArguments().getString(RESUME_PERSONAL);
                    String permaAddress = getArguments().getString(PERMANENT_ADDRESS);
                    String presentAddress = getArguments().getString(PRESENT_ADDRESS);
                    String eduQualifications = getArguments().getString(EDU_QUALIFICATIONS);
                    bundle.putString(RESUME_PERSONAL, personalDetails);
                    bundle.putString(PERMANENT_ADDRESS, permaAddress);
                    bundle.putString(PRESENT_ADDRESS, presentAddress);
                    bundle.putString(EDU_QUALIFICATIONS, eduQualifications);
                }
                GeneralDetailsFragment detailsFragment = new GeneralDetailsFragment();
                detailsFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, detailsFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }
            }
        });

        return view;
    }

    private void FindAllViews(View view) {
        fresherCheckbox = view.findViewById(R.id.fresherCheckbox);
        addnewExpLayout = view.findViewById(R.id.addnewExpLayout);
        professionalExpLayout = view.findViewById(R.id.professionalExpLayout);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);

        professionalExpModel = new ProfessionalExpModel();
        gson = new Gson();
        professionalArray = new JSONArray();
    }

    private void AddChildProfessionalLayout(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        professionalExpLayout = view.findViewById(R.id.professionalExpLayout);
        View childView = inflater.inflate(R.layout.professional_add_layout, null);
        professionalExpLayout.addView(childView);
    }

}
