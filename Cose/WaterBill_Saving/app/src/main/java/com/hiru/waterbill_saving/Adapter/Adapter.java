package com.hiru.waterbill_saving.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hiru.waterbill_saving.Model.Model;
import com.hiru.waterbill_saving.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Model> {

    LayoutInflater layoutInflater;
    ArrayList<Model> arrayList;
    int mViewID;

    public Adapter(@NonNull Context context, int textViewResourceId, ArrayList<Model>model) {
        super(context, textViewResourceId, model);
        this.arrayList = model;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(mViewID, null);

        Model model = arrayList.get(position);
        if (model != null){
            TextView id = (convertView.findViewById( R.id.IDTv));
            TextView thismonth = (convertView.findViewById(R.id.ThismountTv));
            TextView thismonthunit= (convertView.findViewById(R.id.TmonthuintTv));
            TextView useunit= (convertView.findViewById(R.id.UseunitTv));
            TextView calculate =(convertView.findViewById(R.id.TotleTv));


            if (id != null){
                id.setText(model.getBid());
            }
            if (thismonth != null){
                thismonth.setText(model.getTMoun());
            }
            if (thismonthunit != null){
                thismonthunit.setText(model.getTMun());
            }
            if (useunit != null){
                useunit.setText(model.getUseunit());
            }
            if (calculate != null){
                calculate.setText(model.getTotle());
            }

        }
        return convertView;
    }
}
