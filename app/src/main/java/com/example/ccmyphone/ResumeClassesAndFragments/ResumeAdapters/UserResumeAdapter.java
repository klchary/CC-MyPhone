package com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccmyphone.R;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.UserResume;

import java.util.ArrayList;

/**
 * Created by CHINNA CHARY on Friday, 21 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters
 **/
public class UserResumeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<UserResume> userResumeArrayList;

    public UserResumeAdapter(Context context, ArrayList<UserResume> userResumeArrayList) {
        this.context = context;
        this.userResumeArrayList = userResumeArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (userResumeArrayList.size() > 0) {
            return userResumeArrayList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final UserResumeAdapter.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new UserResumeAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.userresume_viewlayout, parent, false);

            listViewHolder.userImageIcon = convertView.findViewById(R.id.userImageIcon);
            listViewHolder.userRemove = convertView.findViewById(R.id.userRemove);
            listViewHolder.userName = convertView.findViewById(R.id.userName);
            listViewHolder.userMobile = convertView.findViewById(R.id.userMobile);
            listViewHolder.userRDetails = convertView.findViewById(R.id.userRDetails);
            listViewHolder.userDatabaseId = convertView.findViewById(R.id.userDatabaseId);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (UserResumeAdapter.ViewHolder) convertView.getTag();
        }

        listViewHolder.userDatabaseId.setText(userResumeArrayList.get(position).getId());
        listViewHolder.userName.setText(userResumeArrayList.get(position).getId() + ". " + userResumeArrayList.get(position).getName());
        listViewHolder.userMobile.setText(userResumeArrayList.get(position).getMobile());
        listViewHolder.userRDetails.setText(userResumeArrayList.get(position).getDescription());

        return convertView;
    }

    private class ViewHolder {
        ImageView userImageIcon, userRemove;
        TextView userName, userMobile, userRDetails, userDatabaseId;
    }
}
