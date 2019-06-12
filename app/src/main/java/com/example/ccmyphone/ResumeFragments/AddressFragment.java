package com.example.ccmyphone.ResumeFragments;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.ccmyphone.R;

import static com.example.ccmyphone.ApplicationConstants.PERMANENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.PRESENT_ADDRESS;
import static com.example.ccmyphone.ApplicationConstants.RESUME_PERSONAL;
import static com.example.ccmyphone.ApplicationConstants.RESUME_SHARED_VALUES;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {

    String TAG = "AddressFragment";

    EditText etPresentAd, etPermanentAd;
    CheckBox preAdasPerAd;

    Button btnBack, btnNext;
    SharedPreferences preferences;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        etPermanentAd = view.findViewById(R.id.etPermanentAd);
        etPresentAd = view.findViewById(R.id.etPresentAd);
        preAdasPerAd = view.findViewById(R.id.preAdasPerAd);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);

        preferences = getActivity().getSharedPreferences(RESUME_SHARED_VALUES, Context.MODE_PRIVATE);
        String permAddre = preferences.getString(PERMANENT_ADDRESS, "");
        String presAddre = preferences.getString(PRESENT_ADDRESS, "");
        if (!"".equalsIgnoreCase(permAddre)) {
            etPermanentAd.setText(permAddre);
        }
        if (!"".equalsIgnoreCase(presAddre)) {
            etPresentAd.setText(presAddre);
        }

        preAdasPerAd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPresentAd.setText(etPermanentAd.getText().toString());
                } else {
                    etPresentAd.setText("");
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalProFragment personalProFragment = new PersonalProFragment();
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, personalProFragment)
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).commit();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EducationalFragment educationalFragment = new EducationalFragment();
                String permanentAddress = etPermanentAd.getText().toString().trim();
                String presentAddress = etPresentAd.getText().toString().trim();
                Bundle bundle = new Bundle();
                Bundle bundle1 = getArguments();
                if (bundle1 != null) {
                    String personalDetails = getArguments().getString(RESUME_PERSONAL);
                    bundle.putString(RESUME_PERSONAL, personalDetails);
                }
                bundle.putString(PERMANENT_ADDRESS, permanentAddress);
                bundle.putString(PRESENT_ADDRESS, presentAddress);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PERMANENT_ADDRESS, permanentAddress);
                editor.putString(PRESENT_ADDRESS, presentAddress);
                editor.apply();
                educationalFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, educationalFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }
            }
        });

        return view;
    }

}
