package com.example.basecommon.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.basecommon.R;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorWorkType;

import java.util.ArrayList;
import java.util.Map;

public class SupervisorAsAdapter extends ArrayAdapter<SupervisorAS> {
    Context context;
    int layoutResourceID;
    ArrayList data;
    Map<Integer, String> AsItemMap;

    public SupervisorAsAdapter(Context context, int layoutResourceID, ArrayList data){
        super(context, layoutResourceID, data);
        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.data = data;
    }

    public void setAsItemMap(Map<Integer, String> asItemMap) {
        AsItemMap = asItemMap;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        View row = converView;
        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_view_as_item_row, null);

        }

        SupervisorAS item = (SupervisorAS) data.get(position);

        if(item != null){
            TextView textViewDong = (TextView) row.findViewById(R.id.textViewDong);
            TextView textViewSupervisor = (TextView) row.findViewById(R.id.textViewSupervisor);
            TextView textViewLocation = (TextView) row.findViewById(R.id.textViewLocation);
            TextView textViewDivision = (TextView) row.findViewById(R.id.textViewDivision);
            TextView textViewPartName = (TextView) row.findViewById(R.id.textViewPartName);
            TextView textViewPartSpec = (TextView) row.findViewById(R.id.textViewPartSpec);
            TextView textViewQty = (TextView) row.findViewById(R.id.textViewQty);
            TextView textViewCause = (TextView) row.findViewById(R.id.textViewCause);
            TextView textViewASDivision = (TextView) row.findViewById(R.id.textViewASDivision);
            TextView textViewComplain = (TextView) row.findViewById(R.id.textViewComplain);
            TextView textViewMeasure = (TextView) row.findViewById(R.id.textViewMeasure);
            TextView textViewMeasurer = (TextView) row.findViewById(R.id.textViewMeasurer);

            textViewDong.setText(item.Dong);
            textViewSupervisor.setText(item.SupervisorName);
            textViewSupervisor.setTag(item.SupervisorCode);
            textViewLocation.setText(item.Ho + "/" + item.HoLocation);
            textViewDivision.setText(AsItemMap.get(item.ItemType));
            textViewPartName.setText(AsItemMap.get(item.Item));
            textViewPartSpec.setText(item.ItemSpecs);
            textViewQty.setText(Integer.toString(item.Quantity));
            textViewCause.setText(AsItemMap.get(item.Reason));
            textViewASDivision.setText(AsItemMap.get(item.ASType));
            textViewComplain.setText(item.Remark);
            textViewMeasure.setText(item.Actions);
            textViewMeasurer.setText(item.ActionEmployee);

        }

        return row;
    }
}
