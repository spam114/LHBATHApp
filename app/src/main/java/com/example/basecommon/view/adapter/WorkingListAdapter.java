package com.example.basecommon.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.WorkingList;
import com.example.basecommon.view.activity.ASListWritePlaceSelect;

import java.util.ArrayList;

public class WorkingListAdapter extends ArrayAdapter<WorkingList> {
    Context context;
    int layoutResourceID;
    ArrayList data;

    public WorkingListAdapter(Context context, int layoutResourceID, ArrayList data){
        super(context,layoutResourceID, data);
        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.data = data;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row = convertView;
        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.working_list_view_row, null);

        }

        WorkingList item = (WorkingList) data.get(position);

        if (item != null) {
            TextView textViewStartTime = (TextView) row.findViewById(R.id.textViewStartTime);
            TextView textViewDong = (TextView) row.findViewById(R.id.textViewDong);
            TextView textViewSupervisorName = (TextView) row.findViewById(R.id.textViewSupervisorName);
            TextView textViewWorkTypeName = (TextView) row.findViewById(R.id.textViewWorkTypeName);
            TextView textViewCustomer = (TextView) row.findViewById(R.id.textViewCustomer);
            TextView textViewLocation = (TextView) row.findViewById(R.id.textViewLocation);
            TextView textViewStatus = (TextView) row.findViewById(R.id.textViewStatus);

            textViewStartTime.setText(item.WorkDate);
            textViewDong.setText(item.Dong);
            textViewSupervisorName.setText(item.SuperVisorName);
            textViewWorkTypeName.setText(item.WorkTypeName);
            textViewCustomer.setText(item.CustomerName);
            textViewLocation.setText(item.LocationName);

            switch (item.StatusFlag){
                case 0:
                    textViewStatus.setText("요청(확인)");
                    break;
                case 1:
                    textViewStatus.setText("작성");
                    break;
                case 2:
                    textViewStatus.setText("확정");
                    break;
                case -1:
                    textViewStatus.setText("요청");
                    break;
            }
        }

        return row;
    }
}
