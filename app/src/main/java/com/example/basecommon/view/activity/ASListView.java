package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ListViewAsItemBinding;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.view.adapter.SupervisorAsAdapter;
import com.example.basecommon.viewModel.SupervisorASItemStandardViewModel;
import com.example.basecommon.viewModel.SupervisorAsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ASListView extends BaseActivity{
    ListViewAsItemBinding binding;
    SupervisorAsViewModel supervisorAsViewModel;
    SupervisorASItemStandardViewModel supervisorASItemStandardViewModel;
    private String LocationName = "";
    private String SupervisorWoNo = "";
    private Map<Integer,String> AsItemMap;
    private ArrayList<SupervisorAS> AsRowData;
    private String Dong;
    private SupervisorAS supervisorAS;
    private String ContractNo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void startProgress() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressOFF2();
            }
        }, 5000);
        progressON("Loading...", handler);
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.list_view_as_item);
        Intent GetIntent = getIntent();

        LocationName = GetIntent.getStringExtra("LocationName");
        SupervisorWoNo = GetIntent.getStringExtra("SupervisorWoNo");
        Dong = GetIntent.getStringExtra("Dong");
        ContractNo = GetIntent.getStringExtra("ContractNo");
        AsItemMap = new HashMap<>();

        supervisorAsViewModel = new ViewModelProvider(this).get(SupervisorAsViewModel.class);
        supervisorASItemStandardViewModel = new ViewModelProvider(this).get(SupervisorASItemStandardViewModel.class);
        observerViewModel();
        supervisorASItemStandardViewModel.GetSupervisorASItemStandardParent();

        UISetting();
        ButtonSetting();
    }

    private void observerViewModel(){
        // 로딩 바
        supervisorAsViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                // 로딩 중이라는 것을 보여준다.
                if (isLoading) {
                    startProgress();
                } else {
                    progressOFF2();
                }
            }
        });

        // AS 작업 목록
        supervisorAsViewModel.SupervisorAsList.observe(this, data -> {
            AsRowData = new ArrayList<>(data);
            SupervisorAsAdapter adapter = new SupervisorAsAdapter(this, R.layout.list_view_as_item_row,AsRowData);
            adapter.setAsItemMap(AsItemMap);
            binding.listView1.setOnItemClickListener(ASListClickListener);
            binding.listView1.setAdapter(adapter);
            binding.listView1.setFocusable(false);
            progressOFF2();
        });

        // AS 아이템 목록 가져오기
        supervisorASItemStandardViewModel.SupervisorASItemStandards.observe(this, data->{
            for(SupervisorASItemStandard item : data){
                AsItemMap.put(item.StandardNo, item.Name);
            }
            supervisorAsViewModel.GetSupervisorAsBySupervisorWoNo(SupervisorWoNo);
        });
    }

    AdapterView.OnItemClickListener ASListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startProgress();
            Intent intent = new Intent(getApplicationContext(), DialogAsItem.class);
            intent.putExtra("SupervisorAsNo",AsRowData.get(position).SupervisorASNo);
            progressOFF2();
            startActivity(intent);
        }
    };

    private void UISetting(){
        binding.tvCustomerLocation.setText(LocationName);
    }

    private void ButtonSetting(){
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogAsItem.class);
                intent.putExtra("LocationName", LocationName);
                intent.putExtra("SupervisorWoNo", SupervisorWoNo);
                intent.putExtra("Dong",Dong);
                intent.putExtra("ContractNo",ContractNo);
                startActivity(intent);
            }
        });
    }
}
