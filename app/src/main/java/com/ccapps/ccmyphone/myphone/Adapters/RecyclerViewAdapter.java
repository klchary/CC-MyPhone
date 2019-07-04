package com.ccapps.ccmyphone.myphone.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccapps.ccmyphone.myphone.Models.InfoModel;
import com.ccapps.ccmyphone.myphone.R;

import java.util.ArrayList;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.ccapps.ccmyphone.myphone
 * Project Name CCMyPhone
 **/

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<InfoModel> infoModel;
    private Context context;

    public RecyclerViewAdapter(ArrayList<InfoModel> infoModel) {
        this.infoModel = infoModel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Description, DescrText;

        public MyViewHolder(View view) {
            super(view);
            Title = view.findViewById(R.id.infoTitle);
            Description = view.findViewById(R.id.infoDetails);
            DescrText = view.findViewById(R.id.infoDetailsText);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {

        String title = infoModel.get(i).getInfoTitle();
        String description = infoModel.get(i).getInfoDetail();
        String descrText = infoModel.get(i).getInfoDetailText();

        myViewHolder.Title.setText(title);
        myViewHolder.Description.setText(description);
        myViewHolder.DescrText.setText(descrText);

        if (myViewHolder.DescrText.getText().toString().equals("") || myViewHolder.DescrText.getText().toString().isEmpty()) {
            myViewHolder.DescrText.setVisibility(View.GONE);
        } else {
            myViewHolder.DescrText.setVisibility(View.VISIBLE);
        }

        final int position = myViewHolder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        if (infoModel != null) {
            return infoModel.size();
        } else {
            return 0;
        }
    }

}
