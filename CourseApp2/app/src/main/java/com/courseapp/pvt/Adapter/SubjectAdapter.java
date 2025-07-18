package com.courseapp.pvt.Adapter;

import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.courseapp.pvt.Model.SubjectModel;
import com.courseapp.pvt.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class SubjectAdapter extends ArrayAdapter<SubjectModel> {

    LayoutInflater layoutInflater;
    ArrayList<SubjectModel> arrayList;
    int mViewID;

    public SubjectAdapter(Context context, int textViewResourceId, ArrayList<SubjectModel> model) {
        super(context, textViewResourceId, model);
        this.arrayList = model;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewID = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(mViewID, null);

        SubjectModel subjectModel = arrayList.get(position);

        if (subjectModel != null) {
            TextView uname = (TextView) convertView.findViewById(R.id.username);
            TextView sub = (TextView) convertView.findViewById(R.id.subject);
            TextView msg = (TextView) convertView.findViewById(R.id.Vmessage);
            if (uname != null) {
                uname.setText(subjectModel.getUser());
            }
            if (sub != null) {
                sub.setText(subjectModel.getSubject());
            }
            if (msg != null) {
                msg.setText(subjectModel.getMessage());
            }
        }

        return convertView;
    }

}
