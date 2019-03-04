package com.example.ccmyphone;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ccmyphone.Adapters.RecyclerViewAdapter;
import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.OtherClasses.RecyclerTouchListener;
import com.example.ccmyphone.OtherClasses.RecyclerviewDivider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkFragment extends Fragment {

    private String title;
    private int page;
    public static final String TAG = "NetworkFragment";

    RecyclerView simRecyclreview;
    ArrayList<InfoModel> simInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter simAdapter;

    String simType;

    public NetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network, container, false);

        simRecyclreview = view.findViewById(R.id.recyclervewSIM);
        simInfoArray = new ArrayList<InfoModel>();

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            simType = String.valueOf(manager.getPhoneCount());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            simInfoArray.add(new InfoModel("IMEI 1", manager.getDeviceId(0)));
            simInfoArray.add(new InfoModel("IMEI 2", manager.getDeviceId(1)));
        }

        //        String simCarrierId = String.valueOf(manager.getSimCarrierId());
//        String simCarrieridName = String.valueOf(manager.getSimCarrierIdName());
        String simState = String.valueOf(manager.getSimState());
        String simState0 = null, simState1 = null, serviceStaee = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            simState0 = String.valueOf(manager.getSimState(0));
            simState1 = String.valueOf(manager.getSimState(1));
            serviceStaee = String.valueOf(manager.getServiceState());
        }
        String dataNetworktype = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataNetworktype = String.valueOf(manager.getDataNetworkType());
        }
        String dataState = String.valueOf(manager.getDataState());
        String dataActivity = String.valueOf(manager.getDataActivity());
//        String signalStrenth = String.valueOf(manager.getSignalStrength());


        simInfoArray.add(new InfoModel("SIM Count", simType));
        simInfoArray.add(new InfoModel("Defualt Device ID", manager.getDeviceId()));
        simInfoArray.add(new InfoModel("SIM Serial No.", manager.getSimSerialNumber()));
        simInfoArray.add(new InfoModel("SIM No.", manager.getLine1Number()));
        simInfoArray.add(new InfoModel("SIM Country ISO", manager.getSimCountryIso()));
        simInfoArray.add(new InfoModel("SIM Operator", manager.getSimOperator()));
        simInfoArray.add(new InfoModel("SIM Operator Name", manager.getSimOperatorName()));
//        simInfoArray.add(new InfoModel("SIM Carrier ID", simCarrierId));
//        simInfoArray.add(new InfoModel("SIM CarrierId Name", simCarrieridName));
        simInfoArray.add(new InfoModel("SIM Software Version", manager.getDeviceSoftwareVersion()));
        simInfoArray.add(new InfoModel("SIM State", simState));
        simInfoArray.add(new InfoModel("SIM State0", simState0));
        simInfoArray.add(new InfoModel("SIM State1", simState1));
        simInfoArray.add(new InfoModel("SIM Data NetworType", dataNetworktype));
        simInfoArray.add(new InfoModel("SIM Data State", dataState));
        simInfoArray.add(new InfoModel("SIM Data Activity", dataActivity));
        simInfoArray.add(new InfoModel("SIM Nai", manager.getNai()));
        simInfoArray.add(new InfoModel("Service State", serviceStaee));
//        simInfoArray.add(new InfoModel("Signal Strenth", signalStrenth));

        simAdapter = new RecyclerViewAdapter(simInfoArray);
        simRecyclreview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        simRecyclreview.setLayoutManager(mLayoutManager);
        simRecyclreview.addItemDecoration(new RecyclerviewDivider(getActivity()));
        simRecyclreview.setItemAnimator(new DefaultItemAnimator());
        simRecyclreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        simRecyclreview.setAdapter(simAdapter);

        simRecyclreview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), simRecyclreview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = simInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = simInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

}
