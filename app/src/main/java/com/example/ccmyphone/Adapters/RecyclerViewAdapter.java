package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccmyphone.Models.InfoModel;
import com.example.ccmyphone.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<InfoModel> infoModel;
    private Context context;

    public RecyclerViewAdapter(ArrayList<InfoModel> infoModel) {
        this.infoModel = infoModel;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Des;

        public MyViewHolder(View view) {
            super(view);
            Title = view.findViewById(R.id.infoTitle);
            Des = view.findViewById(R.id.infoDetails);
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

        myViewHolder.Title.setText(infoModel.get(i).getInfoTitle());
        myViewHolder.Des.setText(infoModel.get(i).getInfoDetail());

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
