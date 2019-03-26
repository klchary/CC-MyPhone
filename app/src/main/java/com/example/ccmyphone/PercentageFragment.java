package com.example.ccmyphone;


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
import android.widget.RelativeLayout;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class PercentageFragment extends Fragment {

    public static final String TAG = "PercentageFragment";
    RelativeLayout scoredLayout, percentageLayout, incDecLayout, gstLayout;
    Spinner spinnerPer;
    ImageView ivSpinner;

    EditText scoredPercentage, total1;
    Button scoredBtn;

    EditText scored, total2;
    Button percentBtn;

    EditText entryOne, entryTwo;
    Button differenceBtn;

    EditText billAmount, gstTax, totalBill;
    Button amountBtn;

    float percentageFloat, totalFloat, scoredFloat;
    int billInt, gstInt;
    float valueOne, valueTwo, difference, diffPercent;

    public PercentageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_percentage, container, false);

        spinnerPer = view.findViewById(R.id.spinnerPer);
        ivSpinner = view.findViewById(R.id.ivSpinner);
        scoredLayout = view.findViewById(R.id.scoredLayout);
        percentageLayout = view.findViewById(R.id.percentageLayout);
        incDecLayout = view.findViewById(R.id.incDecLayout);
        gstLayout = view.findViewById(R.id.gstLayout);

        scoredPercentage = view.findViewById(R.id.scoredPercentage);
        total1 = view.findViewById(R.id.total1);
        scoredBtn = view.findViewById(R.id.scoredBtn);

        entryOne = view.findViewById(R.id.entryOne);
        entryTwo = view.findViewById(R.id.entryTwo);
        differenceBtn = view.findViewById(R.id.differenceBtn);

        scored = view.findViewById(R.id.scored);
        total2 = view.findViewById(R.id.total2);
        percentBtn = view.findViewById(R.id.percentBtn);

        billAmount = view.findViewById(R.id.billAmount);
        gstTax = view.findViewById(R.id.gstTax);
        totalBill = view.findViewById(R.id.totalBill);
        amountBtn = view.findViewById(R.id.amountBtn);

        scoredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateScored();
            }
        });

        percentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePercentage();
            }
        });

        amountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGST();
            }
        });

        differenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDifference();
            }
        });

        spinnerPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equalsIgnoreCase("Percentage")) {
                    percentageLayout.setVisibility(View.VISIBLE);
                    scoredLayout.setVisibility(View.GONE);
                    gstLayout.setVisibility(View.GONE);
                    incDecLayout.setVisibility(View.GONE);
                } else if (selectedItem.equalsIgnoreCase("Score")) {
                    percentageLayout.setVisibility(View.GONE);
                    scoredLayout.setVisibility(View.VISIBLE);
                    gstLayout.setVisibility(View.GONE);
                    incDecLayout.setVisibility(View.GONE);
                } else if (selectedItem.equalsIgnoreCase("GST")) {
                    percentageLayout.setVisibility(View.GONE);
                    scoredLayout.setVisibility(View.GONE);
                    gstLayout.setVisibility(View.VISIBLE);
                    incDecLayout.setVisibility(View.GONE);
                } else if (selectedItem.equalsIgnoreCase("Increased / Decreased")) {
                    percentageLayout.setVisibility(View.GONE);
                    scoredLayout.setVisibility(View.GONE);
                    gstLayout.setVisibility(View.GONE);
                    incDecLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                percentageLayout.setVisibility(View.VISIBLE);
                scoredLayout.setVisibility(View.GONE);
                gstLayout.setVisibility(View.GONE);
            }
        });

        ivSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPer.performClick();
            }
        });


        return view;
    }

    private void calculatePercentage() {
        scoredFloat = Float.parseFloat(scored.getText().toString());
        totalFloat = Float.parseFloat(total2.getText().toString());
//        decimalFormat.format(percentage);
        if (scoredFloat <= totalFloat) {
            percentageFloat = (scoredFloat / totalFloat) * 100;
            Log.d(TAG, "Percentage =" + percentageFloat);
            percentBtn.setText("" + percentageFloat);
        } else {
            percentBtn.setText("et1 should be smaller than et2");
        }
    }

    private void calculateScored() {
        percentageFloat = Float.parseFloat(scoredPercentage.getText().toString());
        totalFloat = Float.parseFloat(total1.getText().toString());
//        decimalFormat.format(percentage);
        if (percentageFloat <= totalFloat) {
            scoredFloat = (percentageFloat * totalFloat) / 100;
            Log.d(TAG, "Scored =" + scoredFloat);
            scoredBtn.setText("" + scoredFloat);
        } else {
            scoredBtn.setText("et1 should be smaller sthan et2");
        }
    }

    public void calculateGST() {
        billInt = Integer.parseInt(billAmount.getText().toString());
        if (billInt >= 10000) {
            gstInt = billInt * 18 / 100;
            gstTax.setText(gstInt + "");
            totalBill.setText(billInt + gstInt + "");
            Log.d(TAG, "text2 =" + gstInt);
        } else {
            gstInt = 0;
            gstTax.setText(gstInt + "");
            totalBill.setText(billInt + "");
        }
        Log.d(TAG, "text1+2 =" + totalBill.getText().toString());
    }

    private void calculateDifference() {
        valueOne = Float.parseFloat(entryOne.getText().toString());
        valueTwo = Float.parseFloat(entryTwo.getText().toString());
//        decimalFormat.format(percentage);
        if (valueOne < valueTwo) {
            difference = valueTwo - valueOne;
            diffPercent = (difference / valueTwo) * 100;
        } else if (valueOne > valueTwo) {
            difference = valueOne - valueTwo;
            diffPercent = (difference / valueOne) * 100;
        } else if (valueOne == valueTwo) {
            difference = 0;
            diffPercent = 0;
        }
        differenceBtn.setText("Difference is :" + difference + "\n" + "Difference Percentage is :" + diffPercent + "%");


    }

}
