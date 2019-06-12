package com.example.ccmyphone.ResumeFragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ccmyphone.Models.EduQualifications;
import com.example.ccmyphone.Models.PersonalProResume;
import com.example.ccmyphone.Models.UserDetails;
import com.example.ccmyphone.OtherClasses.Utils;
import com.example.ccmyphone.R;
import com.google.gson.Gson;

import java.util.Calendar;

import static com.example.ccmyphone.ApplicationConstants.RESUME_PERSONAL;
import static com.example.ccmyphone.ApplicationConstants.RESUME_SHARED_VALUES;
import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalProFragment extends Fragment {

    String TAG = "PersonalProFragment";

    TextInputLayout tilNickName, tilFullName, tilFatherName, tilMotherName, tilMobileNo, tilEmailId, tilLandLine, tilDoB;
    EditText etNickName, etFullName, etFatherName, etMotherName, etMobileNo, etEmailId, etLandLine, etDoB;
    RadioGroup radioGrGender;
    RadioButton radiobtnMale, radiobtnFeMale;
    Spinner spinnerMStatus, spinnerNationality;
    ImageButton dobDatePicker;
    Button btnNext;

    DatePickerDialog pickerDialog;
    int yearInt, monthInt, dayInt;

    String nickNameStr, fullNameStr, fatherNameStr, motherNameStr, mobileNoStr, emailIdStr, landlineStr, dobStr;
    String mStatusStr, nationalityStr, genderStr;

    Gson gson = new Gson();
    SharedPreferences preferences;
    PersonalProResume personalProResume;

    public PersonalProFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_pro, container, false);
        FindAllViews(view);
        preferences = getActivity().getSharedPreferences(RESUME_SHARED_VALUES, Context.MODE_PRIVATE);
        String personalDetails = preferences.getString(RESUME_PERSONAL, null);
        if (personalDetails != null) {
            Log.d(TAG, "personalDetails " + personalDetails);
            personalProResume = gson.fromJson(personalDetails, PersonalProResume.class);
            etNickName.setText(personalProResume.getNickName());
            etFullName.setText(personalProResume.getFullName());
            etFatherName.setText(personalProResume.getFatherName());
            etMotherName.setText(personalProResume.getMotherName());
            etMobileNo.setText(personalProResume.getMobileNo());
            etEmailId.setText(personalProResume.getEmailId());
            etLandLine.setText(personalProResume.getLandLineNo());
            etDoB.setText(personalProResume.getDateofBirth());
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickNameStr = etNickName.getText().toString().trim();
                fullNameStr = etFullName.getText().toString().trim();
                fatherNameStr = etFatherName.getText().toString().trim();
                motherNameStr = etMotherName.getText().toString().trim();
                mobileNoStr = etMobileNo.getText().toString().trim();
                emailIdStr = etEmailId.getText().toString().trim();
                landlineStr = etLandLine.getText().toString().trim();
                dobStr = etDoB.getText().toString().trim();

//                if (isValidDetails()) {
                PersonalProResume personalProResume = new PersonalProResume();
                personalProResume.setFullName(fullNameStr);
                personalProResume.setFatherName(fatherNameStr);
                personalProResume.setMotherName(motherNameStr);
                personalProResume.setMobileNo(mobileNoStr);
                personalProResume.setEmailId(emailIdStr);
                personalProResume.setDateofBirth(dobStr);
                personalProResume.setGender(genderStr);
                personalProResume.setNationality(nationalityStr);
                personalProResume.setmStatus(mStatusStr);

                if (!nickNameStr.isEmpty()) {
                    personalProResume.setNickName(nickNameStr);
                }
                if (!landlineStr.isEmpty()) {
                    personalProResume.setLandLineNo(landlineStr);
                }

                AddressFragment addressFragment = new AddressFragment();
                String personalProResStr = gson.toJson(personalProResume);
                Bundle bundle = new Bundle();
                bundle.putString(RESUME_PERSONAL, personalProResStr);
                Log.d(TAG, "PerProDetails " + personalProResStr);
                preferences.edit().putString(RESUME_PERSONAL, personalProResStr).apply();
                addressFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, addressFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }

//                }
            }
        });

        spinnerMStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusStr = spinnerMStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationalityStr = spinnerNationality.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGrGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = radioGrGender.getCheckedRadioButtonId();
                switch (radioButtonID) {
                    case R.id.radiobtnMale:
                        genderStr = "Male";
                        break;
                    case R.id.radiobtnFeMale:
                        genderStr = "Female";
                        break;
                }
//                RadioButton radioButton = radioGrGender.findViewById(radioButtonID);
//                genderStr = radioButton.getText().toString();
            }
        });

        dobDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                yearInt = c.get(Calendar.YEAR);
                monthInt = c.get(Calendar.MONTH);
                dayInt = c.get(Calendar.DAY_OF_MONTH);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            int monthN = month + 1;
                            if (monthN < 10) {
                                monthN = Integer.parseInt("0" + monthN);
                            }
                            if (dayOfMonth < 10) {
                                dayOfMonth = Integer.parseInt("0" + dayOfMonth);
                            }
                            etDoB.setText(getString(R.string.dob_selected_date, dayOfMonth, monthN, year));
                        }
                    }, yearInt, monthInt, dayInt);
                    pickerDialog.show();
                }
            }
        });

        return view;
    }

    private boolean isValidDetails() {
        Utils utils = new Utils();

        if ("".equalsIgnoreCase(mStatusStr)) {
            Toast.makeText(getActivity(), "Please select Marital Status", Toast.LENGTH_LONG).show();
        }
        if ("".equalsIgnoreCase(nationalityStr)) {
            Toast.makeText(getActivity(), "Please select your Nationality", Toast.LENGTH_LONG).show();
        }
        if ("".equalsIgnoreCase(genderStr)) {
            Toast.makeText(getActivity(), "Please select Gender", Toast.LENGTH_LONG).show();
        }

        boolean isValidFullName = utils.CheckValidName(fullNameStr, tilFullName);
        boolean isValidFatherName = utils.CheckValidName(fatherNameStr, tilFatherName);
        boolean isValidMotherName = utils.CheckValidName(motherNameStr, tilMotherName);
        boolean isValidMobile = utils.CheckValidMobile(mobileNoStr, tilMobileNo);
        boolean isValidEmail = utils.CheckValidEmail(emailIdStr, tilEmailId);
        boolean isValidDoB = utils.CheckValidDOB(dobStr, tilDoB);
        boolean validDetails = !"".equalsIgnoreCase(mStatusStr) && !"".equalsIgnoreCase(nationalityStr) && !"".equalsIgnoreCase(genderStr);

        return isValidFullName && isValidFatherName && isValidMotherName && isValidMobile && isValidEmail && isValidDoB && validDetails;
    }

    private void FindAllViews(View view) {
        tilNickName = view.findViewById(R.id.tilNickName);
        tilFullName = view.findViewById(R.id.tilFullName);
        tilFatherName = view.findViewById(R.id.tilFatherName);
        tilMotherName = view.findViewById(R.id.tilMotherName);
        tilMobileNo = view.findViewById(R.id.tilMobileNo);
        tilEmailId = view.findViewById(R.id.tilEmailId);
        tilLandLine = view.findViewById(R.id.tilLandLine);
        tilDoB = view.findViewById(R.id.tilDoB);

        etNickName = view.findViewById(R.id.etNickName);
        etFullName = view.findViewById(R.id.etFullName);
        etFatherName = view.findViewById(R.id.etFatherName);
        etMotherName = view.findViewById(R.id.etMotherName);
        etMobileNo = view.findViewById(R.id.etMobileNo);
        etEmailId = view.findViewById(R.id.etEmailId);
        etLandLine = view.findViewById(R.id.etLandLine);
        etDoB = view.findViewById(R.id.etDoB);

        radioGrGender = view.findViewById(R.id.radioGrGender);
        radiobtnMale = view.findViewById(R.id.radiobtnMale);
        radiobtnFeMale = view.findViewById(R.id.radiobtnFeMale);
        spinnerMStatus = view.findViewById(R.id.spinnerMStatus);
        spinnerNationality = view.findViewById(R.id.spinnerNationality);

        dobDatePicker = view.findViewById(R.id.dobDatePicker);
        btnNext = view.findViewById(R.id.btnNext);
    }

}
