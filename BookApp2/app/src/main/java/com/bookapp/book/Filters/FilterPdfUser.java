package com.bookapp.book.Filters;

import android.widget.Filter;

import com.bookapp.book.Adapters.AdapterPdfUser;
import com.bookapp.book.Models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfUser extends Filter {
    public ArrayList<ModelPdf> filterList;
    public AdapterPdfUser adapterPdfUser;


    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
    }


    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results = new Filter.FilterResults();

        if (constraint != null && constraint.length() > 0) {

            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filtereModels = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
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
        adapterPdfUser.filterList = (ArrayList<ModelPdf>) results.values;
        adapterPdfUser.notifyDataSetChanged();
    }
}
