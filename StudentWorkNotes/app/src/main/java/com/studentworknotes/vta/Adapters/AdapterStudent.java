package com.studentworknotes.vta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.studentworknotes.vta.Models.ModelStudent;
import com.studentworknotes.vta.R;

import java.util.ArrayList;

public class AdapterStudent extends ArrayAdapter<ModelStudent> {

    LayoutInflater layoutInflater;
    ArrayList<ModelStudent> arrayList;
    int mViewID;

    public AdapterStudent(@NonNull Context context, int textViewResourceId, ArrayList<ModelStudent> adapter) {
        super(context, textViewResourceId, adapter);
        this.arrayList = adapter;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(mViewID, null);

        ModelStudent modelStudent = arrayList.get(position);

        if (modelStudent != null) {
            TextView stname = (TextView) convertView.findViewById(R.id.stnameTv);
            TextView stemail = (TextView) convertView.findViewById(R.id.stemailTv);

            if (stname != null) {
                stname.setText(modelStudent.getUserName());
            }
            if (stemail != null) {
                stemail.setText(modelStudent.getEmail());
            }
        }

        return convertView;
    }
}
