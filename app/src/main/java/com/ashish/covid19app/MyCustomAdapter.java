package com.ashish.covid19app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<StateModel> {
    private  Context context;
    private List<StateModel> stateModelList;

    private List<StateModel> stateModelListFiltered;

    public MyCustomAdapter(@NonNull Context context, @NonNull List<StateModel> stateModelList) {
        super(context, R.layout.customlist,stateModelList);
        this.context=context;
        this.stateModelList=stateModelList;
        //pointing to list
        this.stateModelListFiltered=stateModelList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customlist,null,true);
        TextView tvStateName=view.findViewById(R.id.tvstateName);
        tvStateName.setText(stateModelListFiltered.get(position).getLoc());
        return view;
    }
    @Override
    public int getCount() {
        return stateModelListFiltered.size();
    }

    @Nullable
    @Override
    public StateModel getItem(int position) {
        return stateModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = stateModelList.size();
                    filterResults.values = stateModelList;

                }else{
                    List<StateModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StateModel itemsModel:stateModelList){
                        if(itemsModel.getLoc().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                stateModelListFiltered = (List<StateModel>) results.values;
                affectedStates.stateModelList = (List<StateModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

}
