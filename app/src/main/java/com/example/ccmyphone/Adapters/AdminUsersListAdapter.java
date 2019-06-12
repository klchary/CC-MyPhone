package com.example.ccmyphone.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccmyphone.R;

import java.util.ArrayList;

/**
 * Created by CHINNA CHARY on Friday, 24 May 2019
 * Package Name com.example.ccmyphone
 * Project Name CCMyPhone
 **/

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
            listViewHolder.userNameContactsAP = convertView.findViewById(R.id.userNameContactsAP);
            listViewHolder.userImageIcon = convertView.findViewById(R.id.userImageIcon);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (AdminUsersListAdapter.ViewHolder) convertView.getTag();
        }

        listViewHolder.userMobileAP.setText(list.get(position));

        String mobileNumber = listViewHolder.userMobileAP.getText().toString().trim();
        String contactName = getContactName(mobileNumber, context);

        listViewHolder.userNameContactsAP.setText(contactName);

        if (mobileNumber.equalsIgnoreCase("9581474449")){
            listViewHolder.userImageIcon.setBackground(context.getResources().getDrawable(R.drawable.admin_icon));
        }else {
            listViewHolder.userImageIcon.setBackground(context.getResources().getDrawable(R.drawable.user_icon));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView userMobileAP, userNameContactsAP;
        ImageView userImageIcon;
    }


    public boolean contactExists(Context context, String number) {
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID,
                    ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
            try (Cursor cur = context.getContentResolver().query(lookupUri,
                    mPhoneNumberProjection, null, null, null)) {
                if (cur != null && cur.moveToFirst()) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }// contactExists

    public String getContactName(final String phoneNumber, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        String contactName = "";
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
                Log.d(TAG, "Contact Name " + contactName);
            }
            cursor.close();
        }
        return contactName;
    }

}
