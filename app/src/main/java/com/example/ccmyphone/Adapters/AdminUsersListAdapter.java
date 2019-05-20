package com.example.ccmyphone.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccmyphone.Models.UserDetails;
import com.example.ccmyphone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminUsersListAdapter extends BaseAdapter {

    private String TAG = "AdminUsersListAdapter";
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<String> list;

    public AdminUsersListAdapter(Context context, ArrayList<String> list) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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

        AdminUsersListAdapter.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new AdminUsersListAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adminpanel_userslist, parent, false);

            listViewHolder.userMobileAP = convertView.findViewById(R.id.userMobileAP);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (AdminUsersListAdapter.ViewHolder) convertView.getTag();
        }

        listViewHolder.userMobileAP.setText(list.get(position));

//        listViewHolder.userMobileAP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child(list.get(position));
//                Log.d(TAG, "databaseREference " + database);
//                database.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            String userMobileData = dataSnapshot.child("userMobile").getValue().toString();
//                            String userPasswordData = dataSnapshot.child("password").getValue().toString();
//                            String userNameData = dataSnapshot.child("userName").getValue().toString();
//                            Log.d(TAG, "userDetails " + userMobileData + userNameData + userPasswordData);
//
//                            final Dialog alertLayout = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
//                            alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            alertLayout.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            alertLayout.setContentView(R.layout.userview_dialoglayout);
//
//                            TextView userViewMobile = alertLayout.findViewById(R.id.userViewMobile);
//                            TextView userViewName = alertLayout.findViewById(R.id.userViewName);
//                            ImageView userViewIcon = alertLayout.findViewById(R.id.userViewIcon);
//                            TextView userViewPassword = alertLayout.findViewById(R.id.userViewPassword);
//
//                            userViewMobile.setText(userMobileData);
//                            userViewName.setText(userNameData);
//                            userViewPassword.setText(userPasswordData);
//
//                            alertLayout.show();
//                        }else {
//                            Log.d(TAG, "dataSnapshot Not Exists... " + dataSnapshot + dataSnapshot.exists());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        TextView userMobileAP, userNameAP;
    }

}
