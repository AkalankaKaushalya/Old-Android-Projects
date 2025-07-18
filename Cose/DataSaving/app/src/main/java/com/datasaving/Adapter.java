package com.datasaving;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {

    LayoutInflater layoutInflater;
    ArrayList<Model> arrayList;
    int mViewID;

    public Adapter(@NonNull Context context, int textViewResourceId, ArrayList<Model>model) {
        super(context,textViewResourceId,model);
        this.arrayList = model;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(mViewID, null);

        Model model = arrayList.get(position);
        if(model  != null) {
            TextView nic = convertView.findViewById(R.id.nicTv);
            TextView name = convertView.findViewById(R.id.nameTv);

            if (nic != null) {
                nic.setText(model.getNc());
            } if (name != null) {
                name.setText(model.getFn());
            }


        }

        return convertView;
    }
}
