package com.example.list;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class ToDoAdapter extends ArrayAdapter<ToDoModelclz> {
    private Context context;
    private int resource;
    List<ToDoModelclz> toDoModelclzs;

    ToDoAdapter(Context context, int resource, List<ToDoModelclz> toDoModelclzs){
        super(context,resource,toDoModelclzs);
        this.context = context;
        this.resource = resource;
        this.toDoModelclzs = toDoModelclzs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.title);
        TextView descrip = row.findViewById(R.id.descrip);
        TextView sdate = row.findViewById(R.id.date);
        TextView sdname = row.findViewById(R.id.weekd);
        TextView smname = row.findViewById(R.id.month);
        ImageView imageView = row.findViewById(R.id.imageView2);

        ToDoModelclz toDoModelclz = toDoModelclzs.get(position);
        title.setText(toDoModelclz.getTitle());
        descrip.setText(toDoModelclz.getDescription());

        long s = toDoModelclz.getStarted();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(s);

        String data = DateFormat.format("MMMM", calendar).toString();
        smname.setText(data);


        String data1 = DateFormat.format("dd", calendar).toString();
        sdate.setText(data1);

        String data2 = DateFormat.format("E", calendar).toString();
        sdname.setText(data2);



        imageView.setVisibility(row.INVISIBLE);

        if(toDoModelclz.getFinished() > 0){
            imageView.setVisibility((View.VISIBLE));
        }
        return row;
    }
}
