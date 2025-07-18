package com.studentworknotes.vta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.studentworknotes.vta.Models.ModelWork;
import com.studentworknotes.vta.R;

import java.util.ArrayList;

public class AdapterWork extends ArrayAdapter<ModelWork> {

    LayoutInflater layoutInflater;
    ArrayList<ModelWork> arrayList;
    int mViewID;


    public AdapterWork(@NonNull Context context, int textViewResourceId, ArrayList<ModelWork> adapter) {
        super(context, textViewResourceId, adapter);
        this.arrayList = adapter;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(mViewID, null);

        ModelWork modelWork = arrayList.get(position);

        if (modelWork != null) {
            TextView sub = (TextView) convertView.findViewById(R.id.WtitleTv);
            TextView msg = (TextView) convertView.findViewById(R.id.WsubjectTv);

            if (sub != null) {
                sub.setText(modelWork.getTitle());
            }
            if (msg != null) {
                msg.setText(modelWork.getMaseeag());
            }
        }

        return convertView;
    }
}
