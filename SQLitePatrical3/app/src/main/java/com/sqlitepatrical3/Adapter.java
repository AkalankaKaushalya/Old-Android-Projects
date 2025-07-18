package com.sqlitepatrical3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        Model user = arrayAdapter.get(position);

        if (user != null) {
            TextView name = (TextView) convertView.findViewById(R.id.Vname);
            TextView age = (TextView) convertView.findViewById(R.id.Vage);
            TextView nic = (TextView) convertView.findViewById(R.id.Vnic);
            TextView email = (TextView) convertView.findViewById(R.id.email);
            if (name != null) {
                name.setText(user.getUserName());
            }
            if (age != null) {
                age.setText((user.getNIC()));
            }
            if (nic != null) {
                nic.setText((user.getAge()));
            }
            if (email != null) {
                email.setText((user.getEmail()));
            }
        }

        return convertView;
    }

}
