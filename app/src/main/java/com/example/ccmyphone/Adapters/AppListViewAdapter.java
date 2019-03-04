package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccmyphone.Models.AppsListModel;
import com.example.ccmyphone.R;

import java.util.ArrayList;

public class AppListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<AppsListModel> appsInfoArray;

    public AppListViewAdapter(Context context, ArrayList<AppsListModel> appsInfoArray) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.appsInfoArray = appsInfoArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appsInfoArray.size();
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

        AppListViewAdapter.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new AppListViewAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.listview_apps, parent, false);

            listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.appName);
            listViewHolder.installTime = (TextView) convertView.findViewById(R.id.appFirstinstall);
            listViewHolder.updateTime = (TextView) convertView.findViewById(R.id.appLastupdate);
            listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.appIcon);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (AppListViewAdapter.ViewHolder) convertView.getTag();
        }
        listViewHolder.textInListView.setText(appsInfoArray.get(position).getAppName());
        listViewHolder.imageInListView.setImageDrawable(appsInfoArray.get(position).getAppIcon());
        listViewHolder.installTime.setText("First Installed : " + appsInfoArray.get(position).getInstalledTime());
        listViewHolder.updateTime.setText("Last Updated : " + appsInfoArray.get(position).getUpdateTime());
        String packageName = appsInfoArray.get(position).getPackageName();

        listViewHolder.imageInListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = appsInfoArray.get(position).getPackageName();
                Log.d("PackageName", "PackageName " + packageName);
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {

        TextView textInListView, installTime, updateTime;
        ImageView imageInListView;
    }

}
