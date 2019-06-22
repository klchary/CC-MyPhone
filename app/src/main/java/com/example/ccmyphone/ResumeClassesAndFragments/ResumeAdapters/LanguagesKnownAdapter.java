package com.example.ccmyphone.ResumeClassesAndFragments.ResumeAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.LanguagesKnownModel;
import com.example.ccmyphone.R;

import java.util.ArrayList;

/**
 * Created by CHINNA CHARY on Monday, 17 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.Adapters
 **/
public class LanguagesKnownAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<LanguagesKnownModel> languagesKnownArray;

    public LanguagesKnownAdapter(Context context, ArrayList<LanguagesKnownModel> languagesKnownArray) {
        this.context = context;
        this.languagesKnownArray = languagesKnownArray;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (languagesKnownArray.size() > 0) {
            return languagesKnownArray.size();
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

        final LanguagesKnownAdapter.ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new LanguagesKnownAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.languagesview_layout, parent, false);

            listViewHolder.tvLanguage = convertView.findViewById(R.id.tvLanguage);
            listViewHolder.tvRead = convertView.findViewById(R.id.tvRead);
            listViewHolder.tvWrite = convertView.findViewById(R.id.tvWrite);
            listViewHolder.tvSpeak = convertView.findViewById(R.id.tvSpeak);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (LanguagesKnownAdapter.ViewHolder) convertView.getTag();
        }

        boolean isRead = languagesKnownArray.get(position).isRead();
        boolean isWrite = languagesKnownArray.get(position).isWrite();
        boolean isSpeak = languagesKnownArray.get(position).isSpeak();

        listViewHolder.tvLanguage.setText(languagesKnownArray.get(position).getLanguageName());
        listViewHolder.tvRead.setText("Read");
        listViewHolder.tvWrite.setText("Write");
        listViewHolder.tvSpeak.setText("Speak");

        Drawable drawableChecked = context.getResources().getDrawable(R.drawable.ic_checked);
        Drawable drawableUnChecked = context.getResources().getDrawable(R.drawable.ic_unchecked);

        if (isRead) {
            listViewHolder.tvRead.setCompoundDrawablesWithIntrinsicBounds(drawableChecked, null, null, null);
        } else {
            listViewHolder.tvRead.setCompoundDrawablesWithIntrinsicBounds(drawableUnChecked, null, null, null);
        }
        if (isWrite) {
            listViewHolder.tvWrite.setCompoundDrawablesWithIntrinsicBounds(drawableChecked, null, null, null);
        } else {
            listViewHolder.tvWrite.setCompoundDrawablesWithIntrinsicBounds(drawableUnChecked, null, null, null);
        }
        if (isSpeak) {
            listViewHolder.tvSpeak.setCompoundDrawablesWithIntrinsicBounds(drawableChecked, null, null, null);
        } else {
            listViewHolder.tvSpeak.setCompoundDrawablesWithIntrinsicBounds(drawableUnChecked, null, null, null);
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView tvLanguage, tvRead, tvWrite, tvSpeak;
    }
}
