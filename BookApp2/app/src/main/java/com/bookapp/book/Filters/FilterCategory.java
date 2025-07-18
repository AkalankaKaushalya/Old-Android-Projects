package com.bookapp.book.Filters;

import android.widget.Filter;

import com.bookapp.book.Adapters.AdapterCategory;
import com.bookapp.book.Models.ModelCategory;

import java.util.ArrayList;

public class FilterCategory extends Filter {

    ArrayList<ModelCategory> filterList;
    AdapterCategory adapterCategory;


    public FilterCategory(ArrayList<ModelCategory> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCategory> filtereModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getCategory().toUpperCase().contains(constraint)) {
                    filtereModels.add(filterList.get(i));
                }
            }
            results.count = filtereModels.size();
            results.values = filtereModels;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterCategory.categoryArrayList = (ArrayList<ModelCategory>) results.values;
        adapterCategory.notifyDataSetChanged();
    }
}
