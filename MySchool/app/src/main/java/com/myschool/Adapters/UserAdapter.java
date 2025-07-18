package com.myschool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myschool.Models.UsersModel;
import com.myschool.R;
import com.myschool.databinding.StudentRowBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderUser> {

    public ArrayList<UsersModel> userModels;
    StudentRowBinding binding;

    Context context;
    List<UsersModel> usersModelList;

    public UserAdapter(Context context, List<UsersModel> usersModelList) {
        this.context = context;
        this.usersModelList = usersModelList;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = StudentRowBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        UsersModel model = usersModelList.get(position);
        String name = model.getName();
        String email = model.getEmail();
        String number = model.getPhone();
        String type = model.getUserType();


        holder.username.setText(name);
        holder.useremail.setText(email);
        holder.number.setText(number);
        holder.type.setText(type);
    }

    @Override
    public int getItemCount() {
        return usersModelList.size();
    }

    class HolderUser extends RecyclerView.ViewHolder {

        TextView username, useremail, number, type;

        public HolderUser(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userNameTv);
            useremail = itemView.findViewById(R.id.userEmailTv);
            number = itemView.findViewById(R.id.usernumberTv);
            type = itemView.findViewById(R.id.showtypeTv);
        }
    }
}
