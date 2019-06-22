package com.example.ccmyphone.ResumeClassesAndFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters.LanguagesKnownAdapter;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.LanguagesKnownModel;
import com.example.ccmyphone.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

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
public class GeneralDetailsFragment extends Fragment {

    String TAG = "GeneralDetailsFragment";

    EditText etHobbies;
    Button addHobbies, showHideHobbies;
    ListView hobbiesListView;
    ArrayList<String> hobbiesArray;
    ArrayAdapter<String> hobbiesAdapter;

    EditText etLanguage;
    CheckBox cbRead, cbWrite, cbSpeak;
    ImageButton doneAddLanguage;
    Button showHideLanguages;
    ListView languagesListView;
    ArrayList<LanguagesKnownModel> languagesKnownArray;
    LanguagesKnownAdapter languagesKnownAdapter;

    Button btnBack, btnNext;
    Gson gson = new Gson();


    public GeneralDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_details, container, false);
        FindAllViews(view);

        hobbiesArray = new ArrayList<>();
        hobbiesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hobbiesArray);
        hobbiesListView.setAdapter(hobbiesAdapter);

        languagesKnownArray = new ArrayList<>();
        languagesKnownAdapter = new LanguagesKnownAdapter(getActivity(), languagesKnownArray);
        languagesListView.setAdapter(languagesKnownAdapter);

        addHobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hobbyStr = etHobbies.getText().toString().trim();
                if ("".equalsIgnoreCase(hobbyStr)) {
                    etHobbies.setError("Please enter your hobby");
                } else {
                    hobbiesArray.add(hobbyStr);
                    Toast.makeText(getActivity(), hobbyStr + " is added to Hobbies List", Toast.LENGTH_LONG).show();
                    hobbiesAdapter.notifyDataSetChanged();
                    etHobbies.setText("");
                }
            }
        });

        doneAddLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguagesKnownModel languagesKnownModel = new LanguagesKnownModel();
                String languageStr = etLanguage.getText().toString().trim();
                if ("".equalsIgnoreCase(languageStr)) {
                    etLanguage.setError("Please enter Language you Know");
                } else {
                    languagesKnownModel.setLanguageName(languageStr);
                }
                if (cbRead.isChecked()) {
                    languagesKnownModel.setRead(true);
                } else {
                    languagesKnownModel.setRead(false);
                }
                if (cbWrite.isChecked()) {
                    languagesKnownModel.setWrite(true);
                } else {
                    languagesKnownModel.setWrite(false);
                }
                if (cbSpeak.isChecked()) {
                    languagesKnownModel.setSpeak(true);
                } else {
                    languagesKnownModel.setSpeak(false);
                }

                languagesKnownArray.add(languagesKnownModel);
                languagesKnownAdapter.notifyDataSetChanged();
            }
        });

        showHideHobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbiesListView.getVisibility() == View.VISIBLE) {
                    hobbiesListView.setVisibility(View.GONE);
                    showHideHobbies.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_dropup), null);
                } else {
                    hobbiesListView.setVisibility(View.VISIBLE);
                    showHideHobbies.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_dropdown), null);
                }
            }
        });

        showHideLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languagesListView.getVisibility() == View.VISIBLE) {
                    languagesListView.setVisibility(View.GONE);
                    showHideLanguages.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_dropup), null);
                } else {
                    languagesListView.setVisibility(View.VISIBLE);
                    showHideLanguages.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_dropdown), null);
                }
            }
        });

        hobbiesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removedItem = (String) parent.getItemAtPosition(position);
                hobbiesArray.remove(position);
                hobbiesAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), removedItem + " is removed from Hobbies List", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        languagesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.tvLanguage);
                String removedItem = textView.getText().toString();
                languagesKnownArray.remove(position);
                languagesKnownAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), removedItem + " language is removed from the List", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Bundle receivedBundle = getArguments();
                bundle.putStringArrayList(USER_HOBBIES, hobbiesArray);
                JSONArray jsArray = new JSONArray(languagesKnownArray);
                bundle.putString(LANGUAGES_KNOWN, jsArray.toString());
                if (receivedBundle != null) {
                    String personalDetails = getArguments().getString(RESUME_PERSONAL);
                    String permaAddress = getArguments().getString(PERMANENT_ADDRESS);
                    String presentAddress = getArguments().getString(PRESENT_ADDRESS);
                    String eduQualifications = getArguments().getString(EDU_QUALIFICATIONS);
                    String professionalDetails = getArguments().getString(PROFESSIONAL_DETAILS);
                    bundle.putString(RESUME_PERSONAL, personalDetails);
                    bundle.putString(PERMANENT_ADDRESS, permaAddress);
                    bundle.putString(PRESENT_ADDRESS, presentAddress);
                    bundle.putString(EDU_QUALIFICATIONS, eduQualifications);
                    bundle.putString(PROFESSIONAL_DETAILS, professionalDetails);
                }
                GenerateResumeFragment generateResumeFragment = new GenerateResumeFragment();
                generateResumeFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, generateResumeFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }
            }
        });


        return view;
    }

    private void FindAllViews(View view) {
        etHobbies = view.findViewById(R.id.etHobbies);
        addHobbies = view.findViewById(R.id.addHobbies);
        showHideHobbies = view.findViewById(R.id.showHideHobbies);
        hobbiesListView = view.findViewById(R.id.hobbiesListView);

        etLanguage = view.findViewById(R.id.etLanguage);
        cbRead = view.findViewById(R.id.cbRead);
        cbWrite = view.findViewById(R.id.cbWrite);
        cbSpeak = view.findViewById(R.id.cbSpeak);
        doneAddLanguage = view.findViewById(R.id.doneAddLanguage);
        showHideLanguages = view.findViewById(R.id.showHideLanguages);
        languagesListView = view.findViewById(R.id.languagesListView);

        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
    }

}
