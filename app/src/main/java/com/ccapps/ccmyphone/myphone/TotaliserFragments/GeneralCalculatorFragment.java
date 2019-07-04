package com.ccapps.ccmyphone.myphone.TotaliserFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ccapps.ccmyphone.myphone.R;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class GeneralCalculatorFragment extends Fragment implements View.OnClickListener {

    TextView calResultPanel;
    EditText calEntryPanel;
    Button numberZero, numberOne, numberTwo, numberThree, numberFour;
    Button numberFive, numberSix, numberSeven, numberEight, numberNine;
    Button btnDot, btnEqual, btnPlus, btnMinus, btnCross, btnDivide, btnClear;

    int entrylength;
    private int count = 0;
    String entry;

    public GeneralCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_calculator, container, false);
        FindAllViews(view);

        numberZero.setOnClickListener(this);
        numberOne.setOnClickListener(this);
        numberTwo.setOnClickListener(this);
        numberThree.setOnClickListener(this);
        numberFour.setOnClickListener(this);
        numberFive.setOnClickListener(this);
        numberSix.setOnClickListener(this);
        numberSeven.setOnClickListener(this);
        numberEight.setOnClickListener(this);
        numberNine.setOnClickListener(this);

        btnDot.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnCross.setOnClickListener(this);
        btnDivide.setOnClickListener(this);

        entrylength = calEntryPanel.getText().length();
        entry = calEntryPanel.getText().toString();
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry.length() > 0) {
                    if (entry.endsWith(".")) {
                        count = 0;
                    }
                    String newText = entry.substring(0, entry.length() - 1);
                    //to delete the data contained in the brackets at once
                    if (entry.endsWith(")")) {
                        char[] a = entry.toCharArray();
                        int pos = a.length - 2;
                        int counter = 1;
                        //to find the opening bracket position
                        for (int i = a.length - 2; i >= 0; i--) {
                            if (a[i] == ')') {
                                counter++;
                            } else if (a[i] == '(') {
                                counter--;
                            }
                            //if decimal is deleted b/w brackets then count should be zero
                            else if (a[i] == '.') {
                                count = 0;
                            }
                            //if opening bracket pair for the last bracket is found
                            if (counter == 0) {
                                pos = i;
                                break;
                            }
                        }
                        newText = entry.substring(0, pos);
                    }
                    //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
                    if (newText.equals("-") || newText.endsWith("sqrt")) {
                        newText = "";
                    }
                    //if pow sign is left at the last
                    else if (newText.endsWith("^"))
                        newText = newText.substring(0, newText.length() - 1);

                    calEntryPanel.setText(newText);
                }

            }
        });
        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calEntryPanel.setText("");
                count = 0;
                return false;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numberZero:
                calEntryPanel.setText(calEntryPanel.getText() + "0");
                break;
            case R.id.numberOne:
                calEntryPanel.setText(calEntryPanel.getText() + "1");
                break;
            case R.id.numberTwo:
                calEntryPanel.setText(calEntryPanel.getText() + "2");
                break;
            case R.id.numberThree:
                calEntryPanel.setText(calEntryPanel.getText() + "3");
                break;
            case R.id.numberFour:
                calEntryPanel.setText(calEntryPanel.getText() + "4");
                break;
            case R.id.numberFive:
                calEntryPanel.setText(calEntryPanel.getText() + "5");
                break;
            case R.id.numberSix:
                calEntryPanel.setText(calEntryPanel.getText() + "6");
                break;
            case R.id.numberSeven:
                calEntryPanel.setText(calEntryPanel.getText() + "7");
                break;
            case R.id.numberEight:
                calEntryPanel.setText(calEntryPanel.getText() + "8");
                break;
            case R.id.numberNine:
                calEntryPanel.setText(calEntryPanel.getText() + "9");
                break;

            case R.id.btnDot:
                if (count == 0 && calEntryPanel.length() != 0) {
                    calEntryPanel.setText(calEntryPanel.getText() + ".");
                    count++;
                }
//                if (entrylength != 0) {
//                    calEntryPanel.setText(calEntryPanel.getText() + ".");
//                } else {
//                    calEntryPanel.setText("0.");
//                }
                break;


        }
    }

    public void FindAllViews(View view){
        calResultPanel = view.findViewById(R.id.calResultPanel);
        calEntryPanel = view.findViewById(R.id.calEntryPanel);

        numberZero = view.findViewById(R.id.numberZero);
        numberOne = view.findViewById(R.id.numberOne);
        numberTwo = view.findViewById(R.id.numberTwo);
        numberThree = view.findViewById(R.id.numberThree);
        numberFour = view.findViewById(R.id.numberFour);
        numberFive = view.findViewById(R.id.numberFive);
        numberSix = view.findViewById(R.id.numberSix);
        numberSeven = view.findViewById(R.id.numberSeven);
        numberEight = view.findViewById(R.id.numberEight);
        numberNine = view.findViewById(R.id.numberNine);

        btnDot = view.findViewById(R.id.btnDot);
        btnEqual = view.findViewById(R.id.btnEqual);
        btnPlus = view.findViewById(R.id.btnPlus);
        btnMinus = view.findViewById(R.id.btnMinus);
        btnCross = view.findViewById(R.id.btnCross);
        btnDivide = view.findViewById(R.id.btnDivide);
        btnClear = view.findViewById(R.id.btnClear);
    }

}
