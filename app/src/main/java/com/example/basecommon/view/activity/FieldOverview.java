package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.FieldOverviewBinding;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.viewModel.ContractViewModel;
import com.example.basecommon.viewModel.SupervisorVIewModel;

public class FieldOverview extends BaseActivity{
    FieldOverviewBinding binding;
    private String LocationName;
    private int LocationNo;
    SupervisorVIewModel supervisorVIewModel;
    ContractViewModel contractViewModel;
    private int AsCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setBar();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.field_overview);
        Intent intent = getIntent();
        LocationNo = intent.getIntExtra("LocationNo",0);
        LocationName = intent.getStringExtra("LocationName");
        supervisorVIewModel = new ViewModelProvider(this).get(SupervisorVIewModel.class);
        contractViewModel = new ViewModelProvider(this).get(ContractViewModel.class);
        observerViewModel();
        contractViewModel.GetSearchLocationData(LocationNo);
        showData();
        ButtonEvent();
        setBarTitle(LocationName);
    }

    private void observerViewModel(){
        supervisorVIewModel.SupervisorWorderList.observe(this, data ->{
            AsCount = data.size();
            binding.ASCount.setText(" ■ A/S 건수 : " + AsCount + "건");
        });
        contractViewModel.contractData.observe(this, data -> {
            String Dates = "";
            for(Contract contract : data){
                Dates += contract.StartDate.substring(0,10) + " ~ " + contract.EndDate.substring(0,10) + "\n";
            }
            Dates += "\b";
            binding.ConstructionDay.setText(" ■ 공사기간 : \n" + Dates);
        });
    }
    private void ButtonEvent(){
        binding.AsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AsItemTreeActivity.class);
                intent.putExtra("LocationName",LocationName);
                startActivity(intent);
            }
        });

        binding.BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ASListWritePlaceSelect.class);
                startActivity(intent);
            }
        });

    }

    private void showData(){
        binding.fieldName.setText(" ■ 현장명: " + LocationName);
        supervisorVIewModel.GetSupervisorWorder(LocationNo);
    }
}
