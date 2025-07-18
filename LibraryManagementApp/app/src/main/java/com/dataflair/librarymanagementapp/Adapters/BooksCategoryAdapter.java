package com.dataflair.librarymanagementapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dataflair.librarymanagementapp.Activities.BooksActivity;
import com.dataflair.librarymanagementapp.Activities.EditBookDetailsActivity;
import com.dataflair.librarymanagementapp.Model.Model;
import com.dataflair.librarymanagementapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class BooksCategoryAdapter extends FirebaseRecyclerAdapter<Model, BooksCategoryAdapter.Viewholder> {

    public BooksCategoryAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull BooksCategoryAdapter.Viewholder holder, int position, @NonNull Model model) {


        String imageUrl=model.getCategoryImage();
        String category=model.getCategory().toString();
        Picasso.get().load(imageUrl).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), EditBookDetailsActivity.class);
                intent.putExtra("category",category);
                view.getContext().startActivity(intent);
            }
        });

    }


    @NonNull
    @Override
    public BooksCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_file_admin, parent, false);
        return new BooksCategoryAdapter.Viewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {


        ImageView imageView;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.CategoryImageAdmin);

        }
    }

}

