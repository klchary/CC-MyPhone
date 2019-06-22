package com.example.ccmyphone.ResumeClassesAndFragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ccmyphone.Models.UserDetails;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters.PdfItemViewAdapter;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters.UserResumeAdapter;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.PdfItemViewModel;
import com.example.ccmyphone.R;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.UserResume;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.ccmyphone.ApplicationConstants.APPNAME;
import static com.example.ccmyphone.ApplicationConstants.CC_MyPhone_RESUME;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_DETAIL;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_ID;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_MOBILE;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.COL_NAME;
import static com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeSQLDatabase.TABLE_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumePreviewFragment extends Fragment {

    String TAG = "ResumePreviewFragment";
    GridView resumePreviewGridView, resumeReviewGridView;
    ArrayList<PdfItemViewModel> pdfItemPreviewArray, pdfItemReviewArray;
    ListView usersListView;
    ArrayList<UserResume> userResumeArrayList;
    UserResumeAdapter userResumeAdapter;

    Button createResume;

    SQLiteDatabase SQLdb;
    ResumeSQLDatabase database;

    public ResumePreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resume_preview, container, false);
        createResume = view.findViewById(R.id.createResume);

        resumePreviewGridView = view.findViewById(R.id.resumePreviewGridView);
        pdfItemPreviewArray = new ArrayList<>();
        resumeReviewGridView = view.findViewById(R.id.resumeReviewGridView);
        pdfItemReviewArray = new ArrayList<>();
        usersListView = view.findViewById(R.id.usersListView);

        PrepareReviewPdfItems();

        createResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    PersonalProFragment personalProFragment = new PersonalProFragment();
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, personalProFragment).commit();
                }
            }
        });

        database = new ResumeSQLDatabase(getActivity());
        SQLdb = database.getWritableDatabase();

        userResumeArrayList = new ArrayList<>();
        userResumeAdapter = new UserResumeAdapter(getActivity(), userResumeArrayList);
        usersListView.setAdapter(userResumeAdapter);
        JSONArray jsonArray = getUserResumeData();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (jsonArray.length() > 0) {
                    UserResume userResume = new UserResume();
                    Gson gson = new Gson();

                    String jsonObject = jsonArray.getJSONObject(i).toString();
                    Log.d(TAG, "jsonObject " + jsonObject);
                    userResume = gson.fromJson(jsonObject, UserResume.class);
                    userResumeArrayList.add(userResume);
                    userResumeAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.userDatabaseId);
                String userId = textView.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(APPNAME, APPNAME);
                bundle.putString("USERID", userId);
                GenerateResumeFragment resumeFragment = new GenerateResumeFragment();
                resumeFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.resumeMainLayout, resumeFragment)
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();
                }
            }
        });


        return view;
    }

    private JSONArray getUserResumeData() {
        String[] columns = {COL_ID, COL_NAME, COL_MOBILE, COL_DETAIL};
        String selection = null; // this will select all rows
        Cursor cursor = SQLdb.query(TABLE_NAME, columns, selection, null, null, null, null, null);
        JSONArray resultSet = new JSONArray();
        JSONObject returnObj = new JSONObject();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d(TAG, "CursorGetString(i) " + cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Error Exception " + e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, resultSet.toString());
        return resultSet;
    }

    private void PrepareReviewPdfItems() {
        PdfItemViewAdapter pdfItemViewAdapter = new PdfItemViewAdapter(getActivity(), pdfItemReviewArray);
        resumeReviewGridView.setAdapter(pdfItemViewAdapter);
        File directory = new File(CC_MyPhone_RESUME);
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".pdf")) {
                PdfItemViewModel pdfItemViewModel = new PdfItemViewModel();
                pdfItemViewModel.setPdfName(file.getName());
                pdfItemViewModel.setFilePath(file.getAbsolutePath());
                try {
                    Bitmap bitmap = GeneratePdfImage(file);
                    pdfItemViewModel.setPdfBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfItemReviewArray.add(pdfItemViewModel);
                pdfItemViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private Bitmap GeneratePdfImage(File file) throws IOException {
        Bitmap bitmap = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            final int pageCount = renderer.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                PdfRenderer.Page page = renderer.openPage(0);
                int width = page.getWidth();
                int height = page.getHeight();
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                page.close();
            }
        }
        return bitmap;
    }

}
