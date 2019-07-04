package com.ccapps.ccmyphone.myphone.TotaliserFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ccapps.ccmyphone.myphone.OtherClasses.ConverterClass;
import com.ccapps.ccmyphone.myphone.R;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class ConverterFragment extends Fragment {

    public static final String TAG = "ConverterFragment";

    Spinner spinnerFrom, spinnerTo;
    ImageView spinnerFromiv, spinnerToiv;
    EditText value1, value2;
    Button convertBtn;

    double MM, CM, ME, KM, MILE;
    String fromStr, toStr;

    public ConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        FindAllViews(view);

        MM = CM / 10;
        MM = ME / 1000;
        MM = KM / 1000000;
        MM = MILE / 0.000000621371;

        CM = MM * 10;
        CM = ME / 100;
        CM = KM / 100000;
        CM = MILE / 0.00000621371;

        ME = MM * 1000;
        ME = CM * 100;
        ME = KM / 1000;
        ME = MILE / 0.000621371;

        KM = MM * 1000000;
        KM = CM * 100000;
        KM = ME * 1000;
        KM = MILE / 0.621371;

        MILE = MM * 0.000000621371;
        MILE = CM * 0.00000621371;
        MILE = ME * 0.000621371;
        MILE = KM * 0.621371;


        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equalsIgnoreCase("Milli Metre")) {
                    fromStr = "MM";
                } else if (selectedItem.equalsIgnoreCase("Centi Metre")) {
                    fromStr = "CM";
                } else if (selectedItem.equalsIgnoreCase("Metre")) {
                    fromStr = "ME";
                } else if (selectedItem.equalsIgnoreCase("Kilo Metre")) {
                    fromStr = "KM";
                } else if (selectedItem.equalsIgnoreCase("Mile")) {
                    fromStr = "MILE";
                } else if (selectedItem.equalsIgnoreCase("Feet/Foot")) {
                    fromStr = "FEET";
                } else if (selectedItem.equalsIgnoreCase("INCH")) {
                    fromStr = "INCH";
                } else if (selectedItem.equalsIgnoreCase("YARD")) {
                    fromStr = "YARD";
                }
                Log.d(TAG, "fromStr " + fromStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equalsIgnoreCase("Milli Metre")) {
                    toStr = "MM";
                } else if (selectedItem.equalsIgnoreCase("Centi Metre")) {
                    toStr = "CM";
                } else if (selectedItem.equalsIgnoreCase("Metre")) {
                    toStr = "ME";
                } else if (selectedItem.equalsIgnoreCase("Kilo Metre")) {
                    toStr = "KM";
                } else if (selectedItem.equalsIgnoreCase("Mile")) {
                    toStr = "MILE";
                } else if (selectedItem.equalsIgnoreCase("Feet/Foot")) {
                    toStr = "FEET";
                } else if (selectedItem.equalsIgnoreCase("INCH")) {
                    toStr = "INCH";
                } else if (selectedItem.equalsIgnoreCase("YARD")) {
                    toStr = "YARD";
                }
                Log.d(TAG, "toStr " + toStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double input = Double.parseDouble(value1.getText().toString());
                double result = ConverterClass.ConvertLength(fromStr, toStr, input);
                value2.setText(result + "");
            }
        });


        return view;
    }

    public void FindAllViews(View view){
        spinnerFrom = view.findViewById(R.id.spinnerFrom);
        spinnerTo = view.findViewById(R.id.spinnerTo);
        spinnerFromiv = view.findViewById(R.id.spinnerFromiv);
        spinnerToiv = view.findViewById(R.id.spinnerToiv);
        value1 = view.findViewById(R.id.value1);
        value2 = view.findViewById(R.id.value2);
        convertBtn = view.findViewById(R.id.convertBtn);
    }


}
