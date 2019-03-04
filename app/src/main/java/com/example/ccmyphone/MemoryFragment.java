package com.example.ccmyphone;


import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ccmyphone.Adapters.RecyclerViewAdapter;
import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.OtherClasses.RecyclerTouchListener;
import com.example.ccmyphone.OtherClasses.RecyclerviewDivider;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends Fragment {

    private String title;
    private int page;
    public static final String TAG = "MemoryFragment";

    RecyclerView recyclervewMemory;
    ArrayList<InfoModel> memoryInfoArray;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerViewAdapter memoryAdapter;

    public MemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        memoryInfoArray = new ArrayList<InfoModel>();
        recyclervewMemory = view.findViewById(R.id.recyclervewMemory);

        ActivityManager actManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        bytesToHuman(totalMemory);

        memoryInfoArray.add(new InfoModel("Total InternalMemory", getTotalInternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Available Internal Memory", getAvailableInternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Total External Memory", getTotalExternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Available External Memory", getAvailableExternalMemorySize()));
        memoryInfoArray.add(new InfoModel("Total RAM Size", bytesToHuman(totalMemory)));

        memoryAdapter = new RecyclerViewAdapter(memoryInfoArray);
        recyclervewMemory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclervewMemory.setLayoutManager(mLayoutManager);
        recyclervewMemory.setItemAnimator(new DefaultItemAnimator());
        recyclervewMemory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclervewMemory.setAdapter(memoryAdapter);
        recyclervewMemory.addItemDecoration(new RecyclerviewDivider(getActivity()));

        recyclervewMemory.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclervewMemory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InfoModel infoModel = memoryInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "Click selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                InfoModel infoModel = memoryInfoArray.get(position);
                Toast.makeText(getActivity(), infoModel.getInfoTitle() + "LongClick selected!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    public static boolean externalStorageAvailable() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
        return isSDSupportedDevice && isSDPresent;
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return bytesToHuman(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return bytesToHuman(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalStorageAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return bytesToHuman(availableBlocks * blockSize);
        } else {
            return "Not Available";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalStorageAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return bytesToHuman(totalBlocks * blockSize);
        } else {
            return "Not Available";
        }
    }

    public static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static String bytesToHuman(long size) {
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + " bytes";
        if (size < Mb) return floatForm((double) size / Kb) + " KB";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " MB";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " GB";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " TB";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " PB";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "???";
    }

}
