package com.example.basecommon.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.databinding.AsListViewItemBinding;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.view.activity.FieldOverview;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.AdapterViewHolder>{
    private List<Location> Location_List;
    List<Location> filteredList;

    public LocationAdapter(List<Location> Location_List){
        this.Location_List = Location_List;
        this.filteredList = Location_List;
    }
    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AsListViewItemBinding binding = DataBindingUtil.inflate(inflater,R.layout.as_list_view_item,parent,false);
        return new LocationAdapter.AdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Location item = filteredList.get(position);
        holder.setItem(item,position);
    }

    @Override
    public int getItemCount() {
        return (null != Location_List ? Location_List.size() : 0);
    }

    public void updateAdapter(List<Location> newLocation){
        Location_List.clear();
        Location_List.addAll(newLocation);
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        AsListViewItemBinding binding;

        public AdapterViewHolder(AsListViewItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(Location item, int position){
            binding.AsListItem.setText(item.LocationName);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println( item.LocationName + " / " + item.LocationNo);
                    Intent intent = new Intent(v.getContext(), FieldOverview.class);
                    intent.putExtra("LocationNo",Integer.valueOf(item.LocationNo));
                    intent.putExtra("LocationName",item.LocationName);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
