package com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.PdfItemViewModel;
import com.example.ccmyphone.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by CHINNA CHARY on Friday, 14 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.Adapters
 **/
public class PdfItemViewAdapter extends BaseAdapter {

    private String TAG = "PdfItemViewAdapter";
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<PdfItemViewModel> pdfItemArrayList;

    public PdfItemViewAdapter(Context context, ArrayList<PdfItemViewModel> pdfItemArrayList) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.pdfItemArrayList = pdfItemArrayList;
    }

    @Override
    public int getCount() {
        if (pdfItemArrayList != null) {
            return pdfItemArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final PdfItemViewAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new PdfItemViewAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.pdfitemview_layout, parent, false);

            viewHolder.pdfThumbnailImage = convertView.findViewById(R.id.pdfThumbnailImage);
            viewHolder.pdfName = convertView.findViewById(R.id.pdfName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PdfItemViewAdapter.ViewHolder) convertView.getTag();
        }

//        viewHolder.pdfName.setText(pdfItemArrayList.get(position).getPdfName());
        viewHolder.pdfThumbnailImage.setImageBitmap(pdfItemArrayList.get(position).getPdfBitmap());

        for (int i = 0; i < pdfItemArrayList.size(); i++) {
            if (i == 0) {
                String pdfNameStr = pdfItemArrayList.get(i).getPdfName();
                if (pdfNameStr.length() >= 30) {
                    pdfNameStr = pdfNameStr.substring(0, 30) + "...";
                    viewHolder.pdfName.setText(pdfNameStr);
                } else {
                    viewHolder.pdfName.setText(pdfNameStr);
                }
            }
        }

        viewHolder.pdfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = pdfItemArrayList.get(position).getFilePath();
                final Dialog alertLayout = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Objects.requireNonNull(alertLayout.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertLayout.setContentView(R.layout.pdfview_layout);

                Uri fileUri = Uri.fromFile(new File(filePath));
                PDFView pdfViewer = alertLayout.findViewById(R.id.pdfViewer);
                pdfViewer.fromUri(fileUri).load();

                alertLayout.show();
            }
        });

        viewHolder.pdfThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = pdfItemArrayList.get(position).getFilePath();
                final Dialog alertLayout = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertLayout.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertLayout.setContentView(R.layout.pdfview_layout);

                Uri fileUri = Uri.fromFile(new File(filePath));
                PDFView pdfViewer = alertLayout.findViewById(R.id.pdfViewer);
                pdfViewer.fromUri(fileUri).load();

                alertLayout.show();
            }
        });


        return convertView;
    }

    public class ViewHolder {
        ImageView pdfThumbnailImage;
        TextView pdfName;
    }
}
