package com.example.ccmyphone;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ccmyphone.Adapters.RecyclerViewAdapter;
import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.OtherClasses.RecyclerTouchListener;
import com.example.ccmyphone.OtherClasses.RecyclerviewDivider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralFragment extends Fragment {

    private String title;
    private int page;
    public static final String TAG = "GeneralFragment";

    RecyclerView generalRecyclreview;
    ArrayList<InfoModel> generalInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter generalAdapter;

    static int width;
    static int height;

    public GeneralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        generalRecyclreview = view.findViewById(R.id.recyclervewGeneral);
        generalInfoArray = new ArrayList<InfoModel>();

        getScreenResolution(getActivity());

        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(getActivity(), "LDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "LDPI"));
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(getActivity(), "MDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "MDPI"));
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(getActivity(), "HDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "HDPI"));
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(getActivity(), "XHDPI", Toast.LENGTH_SHORT).show();
                generalInfoArray.add(new InfoModel("Screen Size Type", "XHDPI"));
                break;
        }
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                Toast.makeText(getActivity(), "X - Large screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "X - Large Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Toast.makeText(getActivity(), "Large screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Large Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Toast.makeText(getActivity(), "Normal screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Normal Screen"));
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Toast.makeText(getActivity(), "Small screen", Toast.LENGTH_LONG).show();
                generalInfoArray.add(new InfoModel("Screen Size", "Small Screen"));
                break;
            default:
                Toast.makeText(getActivity(), "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();

        }

        String BaseVersionCode = String.valueOf(Build.VERSION_CODES.BASE);
        String BaseVersionCode1 = String.valueOf(Build.VERSION_CODES.BASE_1_1);
        String SdkIntVersion = String.valueOf(Build.VERSION.SDK_INT);

        generalInfoArray.add(new InfoModel("API Level", SdkIntVersion));
        generalInfoArray.add(new InfoModel("Screen Resolution", width + " X " + height));
        generalInfoArray.add(new InfoModel("Serial Number", Build.SERIAL));
        generalInfoArray.add(new InfoModel("Device Model", Build.MODEL));
        generalInfoArray.add(new InfoModel("ID Number", Build.ID));
        generalInfoArray.add(new InfoModel("Manufacturer Name", Build.MANUFACTURER));
        generalInfoArray.add(new InfoModel("Brand Name", Build.BRAND));
        generalInfoArray.add(new InfoModel("Type", Build.TYPE));
        generalInfoArray.add(new InfoModel("User", Build.USER));
        generalInfoArray.add(new InfoModel("Base Version", BaseVersionCode));
        generalInfoArray.add(new InfoModel("Board", Build.BOARD));
        generalInfoArray.add(new InfoModel("Incremental", Build.VERSION.INCREMENTAL));
        generalInfoArray.add(new InfoModel("SDK Version", SdkIntVersion));
        generalInfoArray.add(new InfoModel("Host", Build.HOST));
        generalInfoArray.add(new InfoModel("FingerPrint", Build.FINGERPRINT));
        generalInfoArray.add(new InfoModel("Bootloader", Build.BOOTLOADER));
        generalInfoArray.add(new InfoModel("Hardware", Build.HARDWARE));
        generalInfoArray.add(new InfoModel("Tags", Build.TAGS));
        generalInfoArray.add(new InfoModel("Display", Build.DISPLAY));
        generalInfoArray.add(new InfoModel("Radio Version", Build.getRadioVersion()));

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy - hh:mm:ss");
        String dateString = formatter.format(new Date(Build.TIME));
        generalInfoArray.add(new InfoModel("Build Time", dateString));

        generalAdapter = new RecyclerViewAdapter(generalInfoArray);
        generalRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        generalRecyclreview.setLayoutManager(mLayoutManager);
        generalRecyclreview.setItemAnimator(new DefaultItemAnimator());
        generalRecyclreview.setAdapter(generalAdapter);
        generalRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        generalRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));

        generalRecyclreview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), generalRecyclreview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = generalInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = generalInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    private String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        return "{" + width + "," + height + "}";
    }

}
