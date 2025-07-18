package com.myschool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myschool.Models.Model;
import com.myschool.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Model> {

    LayoutInflater layoutInflater;
    ArrayList<Model> arrayAdapter;
    int mViewID;


    public Adapter(Context context, int textViewResourceId, ArrayList<Model> model) {
        super(context, textViewResourceId, model);
        this.arrayAdapter = model;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(mViewID, null);

        Model note = arrayAdapter.get(position);

        if (note != null) {
            TextView id = (TextView) convertView.findViewById(R.id.noteidTv);
            TextView title = (TextView) convertView.findViewById(R.id.WtitleTv);
            TextView masseage = (TextView) convertView.findViewById(R.id.WmasseageTv);
            TextView date = (TextView) convertView.findViewById(R.id.WdateTv);

            if (id != null) {
                id.setText(String.valueOf(note.getID()));
            }
            if (title != null) {
                title.setText(note.getTitle());
            }
            if (masseage != null) {
                masseage.setText((note.getMasseage()));
            }
            if (date != null) {
                date.setText((note.getNDate()));
            }

        }

        return convertView;
    }
}
