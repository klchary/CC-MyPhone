package com.example.ccmyphone.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccmyphone.Models.AppsListModel;
import com.example.ccmyphone.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppListViewAdapter extends BaseAdapter {

    private String TAG = "AppListViewAdapter";
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

        final AppListViewAdapter.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new AppListViewAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.listview_apps, parent, false);

            listViewHolder.textInListView = convertView.findViewById(R.id.appName);
            listViewHolder.installTime = convertView.findViewById(R.id.appFirstinstall);
            listViewHolder.updateTime = convertView.findViewById(R.id.appLastupdate);
            listViewHolder.imageInListView = convertView.findViewById(R.id.appIcon);
            listViewHolder.appFullLayout = convertView.findViewById(R.id.appFullLayout);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (AppListViewAdapter.ViewHolder) convertView.getTag();
        }

        final String appName = appsInfoArray.get(position).getAppName();
        final String appPermissionsStr = appsInfoArray.get(position).getPermissions();
        final String appInastalledTimeStr = appsInfoArray.get(position).getInstalledTime();
        final String appLastUpdatedTimeStr = appsInfoArray.get(position).getUpdateTime();
        final Drawable drawableImage = appsInfoArray.get(position).getAppIcon();
        final String packageName = appsInfoArray.get(position).getPackageName();

        listViewHolder.textInListView.setText(appName);
        listViewHolder.imageInListView.setImageDrawable(drawableImage);
        listViewHolder.installTime.setText("First Installed : " + appInastalledTimeStr);
        listViewHolder.updateTime.setText("Last Updated : " + appLastUpdatedTimeStr);

        listViewHolder.imageInListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        listViewHolder.appFullLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog alertLayout = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertLayout.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertLayout.setContentView(R.layout.singleappviewdialog);

                ImageButton closeDialogAppView = alertLayout.findViewById(R.id.closeDialogAppView);
                ImageView appViewIcon = alertLayout.findViewById(R.id.appViewIcon);
                TextView appViewName = alertLayout.findViewById(R.id.appViewName);
                TextView appViewPackageName = alertLayout.findViewById(R.id.appViewPackageName);
                TextView appViewFirstinstall = alertLayout.findViewById(R.id.appViewFirstinstall);
                TextView appViewLastupdate = alertLayout.findViewById(R.id.appViewLastupdate);
                TextView appViewPermissions = alertLayout.findViewById(R.id.appViewPermissions);
                TextView appViewGrantedPermissions = alertLayout.findViewById(R.id.appViewGrantedPermissions);

                appViewName.setText(appName);
                appViewIcon.setImageDrawable(drawableImage);
                appViewFirstinstall.setText("First Installed : " + appInastalledTimeStr);
                appViewLastupdate.setText("Last Updated : " + appLastUpdatedTimeStr);
                appViewPermissions.setText(appPermissionsStr);
                appViewPackageName.setText(packageName);

//                List<String> grantedPermissions = new ArrayList<>();
//                grantedPermissions.add(appsInfoArray.get(position).getGranted());

                ArrayList<String> granted = new ArrayList<String>();
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                            packageName, PackageManager.GET_PERMISSIONS);
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                            granted.add(requestedPermissions[i]);
                            Log.d(TAG, "granted Size " + granted.size());
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                appViewGrantedPermissions.setText(granted.toString());

                closeDialogAppView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertLayout.cancel();
                    }
                });

                alertLayout.show();
            }
        });

        return convertView;
    }

    static class ViewHolder {

        TextView textInListView, installTime, updateTime;
        ImageView imageInListView;
        LinearLayout appFullLayout;
    }

}
