package com.bookapp.book.Filters;

import android.widget.Filter;

import com.bookapp.book.Adapters.AdapterPdfAdmin;
import com.bookapp.book.Models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfAdmin extends Filter {

    ArrayList<ModelPdf> filterList;
    AdapterPdfAdmin adapterPdfAdmin;


    public FilterPdfAdmin(ArrayList<ModelPdf> filterList, AdapterPdfAdmin adapterPdfAdmin) {
        this.filterList = filterList;
        this.adapterPdfAdmin = adapterPdfAdmin;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
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
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>) results.values;
        adapterPdfAdmin.notifyDataSetChanged();
    }
}
