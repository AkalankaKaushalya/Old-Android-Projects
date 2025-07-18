package com.ckfood.pvt.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ckfood.pvt.R;
import com.squareup.picasso.Picasso;

public class BreadViewHolders extends RecyclerView.ViewHolder {
    View BView;
    public BreadViewHolders(@NonNull View itemView) {
        super(itemView);
        BView = itemView;
    }
    public void setDetails(Context ctx, String bname, String bdis, String bprice, String bimg){
        TextView BTitel = BView.findViewById(R.id.rBreadname);
        TextView BDiec = BView.findViewById(R.id.rBreaddiscirp);
        TextView Bprice = BView.findViewById(R.id.rBreadprise);
        ImageView BImg = BView.findViewById(R.id.rBreadimage);

        BTitel.setText(bname);
        BDiec.setText(bdis);
        Bprice.setText(bprice);
        Picasso.get().load(bimg).into(BImg);
    }
}




