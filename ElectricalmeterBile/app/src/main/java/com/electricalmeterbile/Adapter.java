package com.electricalmeterbile;

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

        Model bildata = arrayAdapter.get(position);

        if (bildata != null) {
            TextView bilno = (TextView) convertView.findViewById(R.id.VdilnoTv);
            TextView ldate = (TextView) convertView.findViewById(R.id.VbldayTv);
            TextView nowdate = (TextView) convertView.findViewById(R.id.VbndayTv);
            TextView lu = (TextView) convertView.findViewById(R.id.lunitTv);
            TextView nu = (TextView) convertView.findViewById(R.id.nunitTv);
            TextView nuu = (TextView) convertView.findViewById(R.id.nuseunitTv);
            TextView totle = (TextView) convertView.findViewById(R.id.thismprisTv);


            if (bilno != null) {
                bilno.setText(String.valueOf(bildata.getBilNo()));
            }
            if (ldate != null) {
                ldate.setText(bildata.getLbdate());
            }
            if (nowdate != null) {
                nowdate.setText((bildata.getNbdate()));
            }
            if (lu != null) {
                lu.setText((bildata.getLunit()));
            }
            if (nu != null) {
                nu.setText(bildata.getNunit());
            }
            if (nuu != null) {
                nuu.setText((bildata.getNuseunit()));
            }
            if (totle != null) {
                totle.setText((bildata.getThisMonPris()));
            }

        }

        return convertView;
    }
}
